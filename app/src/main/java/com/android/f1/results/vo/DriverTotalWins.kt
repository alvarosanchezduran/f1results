package com.android.f1.results.vo

data class DriverTotalWins(
    val driver: Driver,
    val total: Int
) {
    fun getTotalString(): String {
        return total.toString()
    }
}
