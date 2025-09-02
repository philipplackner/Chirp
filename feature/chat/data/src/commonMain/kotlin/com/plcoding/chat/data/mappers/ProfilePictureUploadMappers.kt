package com.plcoding.chat.data.mappers

import com.plcoding.chat.data.dto.response.ProfilePictureUploadUrlsResponse
import com.plcoding.chat.domain.models.ProfilePictureUploadUrls

fun ProfilePictureUploadUrlsResponse.toDomain(): ProfilePictureUploadUrls {
    return ProfilePictureUploadUrls(
        uploadUrl = uploadUrl,
        publicUrl = publicUrl,
        headers = headers
    )
}