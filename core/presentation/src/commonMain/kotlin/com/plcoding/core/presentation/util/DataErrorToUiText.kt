package com.plcoding.core.presentation.util

import chirp.core.presentation.generated.resources.Res
import chirp.core.presentation.generated.resources.error_bad_request
import chirp.core.presentation.generated.resources.error_conflict
import chirp.core.presentation.generated.resources.error_disk_full
import chirp.core.presentation.generated.resources.error_forbidden
import chirp.core.presentation.generated.resources.error_no_internet
import chirp.core.presentation.generated.resources.error_not_found
import chirp.core.presentation.generated.resources.error_payload_too_large
import chirp.core.presentation.generated.resources.error_request_timeout
import chirp.core.presentation.generated.resources.error_serialization
import chirp.core.presentation.generated.resources.error_server
import chirp.core.presentation.generated.resources.error_service_unavailable
import chirp.core.presentation.generated.resources.error_too_many_requests
import chirp.core.presentation.generated.resources.error_unable_to_send_message
import chirp.core.presentation.generated.resources.error_unauthorized
import chirp.core.presentation.generated.resources.error_unknown
import com.plcoding.core.domain.util.DataError

fun DataError.toUiText(): UiText {
    val resource = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.NOT_FOUND -> Res.string.error_not_found
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.BAD_REQUEST -> Res.string.error_bad_request
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.UNAUTHORIZED -> Res.string.error_unauthorized
        DataError.Remote.FORBIDDEN -> Res.string.error_forbidden
        DataError.Remote.NOT_FOUND -> Res.string.error_not_found
        DataError.Remote.CONFLICT -> Res.string.error_conflict
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.PAYLOAD_TOO_LARGE -> Res.string.error_payload_too_large
        DataError.Remote.SERVER_ERROR -> Res.string.error_server
        DataError.Remote.SERVICE_UNAVAILABLE -> Res.string.error_service_unavailable
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
        DataError.Connection.NOT_CONNECTED -> Res.string.error_no_internet
        DataError.Connection.MESSAGE_SEND_FAILED -> Res.string.error_unable_to_send_message
    }
    return UiText.Resource(resource)
}