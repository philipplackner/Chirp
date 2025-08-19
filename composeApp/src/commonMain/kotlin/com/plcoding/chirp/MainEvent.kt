package com.plcoding.chirp

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}