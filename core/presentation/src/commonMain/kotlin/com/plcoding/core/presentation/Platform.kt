package com.plcoding.core.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

expect fun platform(): String

@Composable
fun HelloWorld() {
    Scaffold {

    }
}