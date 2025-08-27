package com.plcoding.core.domain.util

class DataErrorException(
    val error: DataError
): Exception()