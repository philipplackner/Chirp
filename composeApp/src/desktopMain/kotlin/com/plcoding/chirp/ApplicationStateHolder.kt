package com.plcoding.chirp

import com.plcoding.chirp.windows.WindowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ApplicationStateHolder(
    private val applicationScope: CoroutineScope
) {

    private val _state = MutableStateFlow(ApplicationState())
    val state = _state
        .onStart {

        }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            _state.value
        )

    fun onAddWindowClick() {
        _state.update { it.copy(
            windows = it.windows + WindowState()
        ) }
    }

    fun onWindowCloseRequest(id: String) {
        _state.update { it.copy(
            windows = it.windows.filter { it.id != id }
        ) }
    }
}