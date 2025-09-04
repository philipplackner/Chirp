package com.plcoding.chat.data.participant

import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.chat.domain.participant.ChatParticipantRepository
import com.plcoding.chat.domain.participant.ChatParticipantService
import com.plcoding.core.domain.auth.AuthInfo
import com.plcoding.core.domain.auth.SessionStorage
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult
import com.plcoding.core.domain.util.Result
import com.plcoding.core.domain.util.onSuccess
import kotlinx.coroutines.flow.first

class OfflineFirstChatParticipantRepository(
    private val sessionStorage: SessionStorage,
    private val chatParticipantService: ChatParticipantService
): ChatParticipantRepository {

    override suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError> {
        return chatParticipantService
            .getLocalParticipant()
            .onSuccess { participant ->
                val currentAuthInfo = sessionStorage.observeAuthInfo().first()
                sessionStorage.set(
                    currentAuthInfo?.copy(
                        user = currentAuthInfo.user.copy(
                            id = participant.userId,
                            username = participant.username,
                            profilePictureUrl = participant.profilePictureUrl
                        )
                    )
                )
            }
    }

    override suspend fun uploadProfilePicture(
        imageBytes: ByteArray,
        mimeType: String
    ): EmptyResult<DataError.Remote> {
        val result = chatParticipantService.getProfilePictureUploadUrl(mimeType)

        if(result is Result.Failure) {
            return result
        }

        val uploadUrls = (result as Result.Success).data
        val uploadResult = chatParticipantService.uploadProfilePicture(
            uploadUrl = uploadUrls.uploadUrl,
            imageBytes = imageBytes,
            headers = uploadUrls.headers
        )

        if(uploadResult is Result.Failure) {
            return uploadResult
        }

        return chatParticipantService
            .confirmProfilePictureUpload(uploadUrls.publicUrl)
            .onSuccess {
                val currentAuthInfo = sessionStorage.observeAuthInfo().first()
                sessionStorage.set(
                    currentAuthInfo?.copy(
                        user = currentAuthInfo.user.copy(
                            profilePictureUrl = uploadUrls.publicUrl
                        )
                    )
                )
            }
    }

    override suspend fun deleteProfilePicture(): EmptyResult<DataError.Remote> {
        return chatParticipantService
            .deleteProfilePicture()
            .onSuccess {
                val authInfo = sessionStorage.observeAuthInfo().first()
                sessionStorage.set(
                    authInfo?.copy(
                        user = authInfo.user.copy(
                            profilePictureUrl = null
                        )
                    )
                )
            }
    }
}