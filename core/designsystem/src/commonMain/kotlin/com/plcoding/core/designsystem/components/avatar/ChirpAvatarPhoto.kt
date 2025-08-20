package com.plcoding.core.designsystem.components.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class AvatarSize(val dp: Dp) {
    SMALL(40.dp), LARGE(60.dp)
}

@Composable
fun ChirpAvatarPhoto(
    displayText: String,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.SMALL,
    imageUrl: String? = null,
    onClick: (() -> Unit)? = null,
    textColor: Color = MaterialTheme.colorScheme.extended.textPlaceholder
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .clickable(
                onClick = { onClick?.invoke() },
                enabled = onClick != null
            )
            .background(MaterialTheme.colorScheme.extended.secondaryFill)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )
        AsyncImage(
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .matchParentSize()
        )
    }
}

@Composable
@Preview
fun ChirpAvatarPhotoPreview() {
    ChirpTheme {
        ChirpAvatarPhoto(
            displayText = "PL",
            size = AvatarSize.LARGE
        )
    }
}