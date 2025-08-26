package com.plcoding.core.data.database

import androidx.sqlite.SQLiteException
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.Result

suspend inline fun <T> safeDatabaseUpdate(update: suspend () -> T): Result<T, DataError.Local> {
    return try {
        Result.Success(update())
    } catch(_: SQLiteException) {
        Result.Failure(DataError.Local.DISK_FULL)
    }
}