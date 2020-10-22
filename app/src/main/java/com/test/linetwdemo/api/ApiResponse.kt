package com.test.linetwdemo.api

import androidx.annotation.Nullable
import retrofit2.Response

class ApiResponse<T> {
    val code: Int

    @Nullable
    val body: T?

    @Nullable
    val errorMessage: String?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            response.errorBody()?.let {
                message = it.toString()
            }
//            val a = message?.trim { it<=' ' }?.isEmpty() ?: false
//            if (message == null || a) {
//                message = response.message()
//            }
            errorMessage = message
            body = null
        }
    }

    val isSuccessful: Boolean get() = code in 200..299
}