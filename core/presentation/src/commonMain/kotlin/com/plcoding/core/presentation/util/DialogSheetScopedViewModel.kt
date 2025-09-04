@file:OptIn(ExperimentalUuidApi::class)

package com.plcoding.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DialogSheetScopedViewModel(
    visible: Boolean,
    scopeId: String = rememberSaveable { Uuid.random().toString() },
    content: @Composable () -> Unit
) {
    val parentOwner = LocalViewModelStoreOwner.current
        ?: throw IllegalStateException("No parent owner found")

    val registry = koinViewModel<ScopedStoreRegistryViewModel>(
        viewModelStoreOwner = parentOwner
    )

    var owner by remember { mutableStateOf<ViewModelStoreOwner?>(null) }

    LaunchedEffect(visible, scopeId) {
        if(visible && owner == null) {
            owner = object : ViewModelStoreOwner {
                override val viewModelStore: ViewModelStore
                    get() = registry.getOrCreate(scopeId)
            }
        } else if(!visible && owner != null) {
            registry.clear(scopeId)
            owner = null
        }
    }

    owner?.let { dialogOwner ->
        CompositionLocalProvider(LocalViewModelStoreOwner provides dialogOwner) {
            content()
        }
    }
}