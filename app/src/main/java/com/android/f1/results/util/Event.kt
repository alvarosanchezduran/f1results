package com.android.f1.results.util

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

}