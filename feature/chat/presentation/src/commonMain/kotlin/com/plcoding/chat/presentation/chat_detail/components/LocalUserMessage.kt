package com.plcoding.chat.presentation.chat_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.delete_for_everyone
import chirp.feature.chat.presentation.generated.resources.reload_icon
import chirp.feature.chat.presentation.generated.resources.retry
import chirp.feature.chat.presentation.generated.resources.you
import com.plcoding.chat.domain.models.ChatMessageDeliveryStatus
import com.plcoding.chat.presentation.model.MessageUi
import com.plcoding.core.designsystem.components.chat.ChirpChatBubble
import com.plcoding.core.designsystem.components.chat.TrianglePosition
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LocalUserMessage(
    message: MessageUi.LocalUserMessage,
    onMessageLongClick: () -> Unit,
    onDismissMessageMenu: () -> Unit,
    onDeleteClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ChirpChatBubble(
                messageContent = message.content,
                sender = stringResource(Res.string.you),
                formattedDateTime = message.formattedSentTime.asString(),
                trianglePosition = TrianglePosition.RIGHT,
                messageStatus = {
                    MessageStatus(
                        status = message.deliveryStatus
                    )
                },
                onLongClick = {
                    onMessageLongClick()
                }
            )

            DropdownMenu(
                expanded = message.isMenuOpen,
                onDismissRequest = onDismissMessageMenu,
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.extended.surfaceOutline
                )
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.delete_for_everyone),
                            color = MaterialTheme.colorScheme.extended.destructiveHover,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    onClick = {
                        onDismissMessageMenu()
                        onDeleteClick()
                    }
                )
            }
        }

        if(message.deliveryStatus == ChatMessageDeliveryStatus.FAILED) {
            IconButton(
                onClick = onRetryClick
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.reload_icon),
                    contentDescription = stringResource(Res.string.retry),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}