package com.plcoding.core.domain.auth

import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote>
}