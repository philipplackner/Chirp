package com.plcoding.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.only_you
import chirp.feature.chat.presentation.generated.resources.you
import com.plcoding.chat.presentation.model.ChatUi
import com.plcoding.core.designsystem.components.avatar.ChirpStackedAvatars
import com.plcoding.core.designsystem.theme.extended
import com.plcoding.core.designsystem.theme.titleXSmall
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChatItemHeaderRow(
    chat: ChatUi,
    isGroupChat: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if(chat.otherParticipants.isNotEmpty()) {
            ChirpStackedAvatars(
                avatars = chat.otherParticipants,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = if(!isGroupChat) {
                    chat.otherParticipants.firstOrNull()?.username
                        ?: stringResource(Res.string.only_you)
                } else {
                    stringResource(Res.string.group_chat)
                },
                style = MaterialTheme.typography.titleXSmall,
                color = MaterialTheme.colorScheme.extended.textPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            if(isGroupChat) {
                val you = stringResource(Res.string.you)
                val formattedUsernames = remember(chat.otherParticipants) {
                    "$you, " + chat.otherParticipants.joinToString {
                        it.username
                    }
                }
                Text(
                    text = formattedUsernames,
                    color = MaterialTheme.colorScheme.extended.textPlaceholder,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}