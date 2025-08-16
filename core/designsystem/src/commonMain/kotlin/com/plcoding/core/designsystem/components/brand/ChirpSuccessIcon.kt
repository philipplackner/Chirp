package com.plcoding.core.designsystem.components.brand

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.success_checkmark
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ChirpSuccessIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = vectorResource(Res.drawable.success_checkmark),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.extended.success,
        modifier = modifier
    )
}