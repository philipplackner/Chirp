package com.plcoding.chirp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.chat.domain.notification.DeviceTokenService
import com.plcoding.chat.domain.notification.PushNotificationService
import com.plcoding.core.data.util.PlatformUtils
import com.plcoding.core.domain.auth.SessionStorage
import com.plcoding.core.domain.util.onFailure
import com.plcoding.core.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage,
    private val pushNotificationService: PushNotificationService,
    private val deviceTokenService: DeviceTokenService
): ViewModel() {

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeSession()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    private var previousRefreshToken: String? = null
    private var currentDeviceToken: String? = null
    private var previousDeviceToken: String? = null

    init {
        viewModelScope.launch {
            val authInfo = sessionStorage.observeAuthInfo().firstOrNull()
            _state.update { it.copy(
                isCheckingAuth = false,
                isLoggedIn = authInfo != null
            ) }
        }
    }

    private fun observeSession() {
        sessionStorage
            .observeAuthInfo()
            .onEach { authInfo ->
                val currentRefreshToken = authInfo?.refreshToken
                val isSessionExpired = previousRefreshToken != null && currentRefreshToken == null
                if(isSessionExpired) {
                    sessionStorage.set(null)
                    _state.update { it.copy(
                        isLoggedIn = false
                    ) }
                    currentDeviceToken?.let {
                        deviceTokenService.unregisterToken(it)
                    }
                    eventChannel.send(MainEvent.OnSessionExpired)
                }

                previousRefreshToken = currentRefreshToken
            }
            .combine(
                pushNotificationService.observeDeviceToken()
            ) { authInfo, deviceToken ->
                currentDeviceToken = deviceToken
                if(authInfo != null && deviceToken != previousDeviceToken && deviceToken != null) {
                    registerDeviceToken(deviceToken, PlatformUtils.getOSName())
                }
            }
            .launchIn(viewModelScope)
    }

    private fun registerDeviceToken(token: String, platform: String) {
        viewModelScope.launch {
            deviceTokenService.registerToken(token, platform)
        }
    }
}