package com.android.f1.results.vo

data class SimpleDriver(
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
