package com.android.f1.results.vo

import com.android.f1.results.vo.Status.ERROR
import com.android.f1.results.vo.Status.LOADING
import com.android.f1.results.vo.Status.SUCCESS
import com.android.f1.results.vo.Status.ERROR_INVALID_DATA

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> errorInvalidData(data: T?): Resource<T> {
            return Resource(ERROR_INVALID_DATA, data, null)
        }
    }
}
