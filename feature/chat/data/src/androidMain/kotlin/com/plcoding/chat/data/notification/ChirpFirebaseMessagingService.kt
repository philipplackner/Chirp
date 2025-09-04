package com.plcoding.chat.data.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.plcoding.chat.domain.notification.DeviceTokenService
import com.plcoding.core.domain.auth.SessionStorage
import com.plcoding.core.domain.platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ChirpFirebaseMessagingService: FirebaseMessagingService() {

    private val deviceTokenService by inject<DeviceTokenService>()
    private val sessionStorage by inject<SessionStorage>()
    private val applicationScope by inject<CoroutineScope>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        applicationScope.launch {
            val authInfo = sessionStorage.observeAuthInfo().first()
            if(authInfo != null) {
                deviceTokenService.registerToken(
                    token = token,
                    platform = "ANDROID"
                )
            }
        }
    }
}