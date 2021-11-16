package com.android.f1.results.util

import com.android.f1.results.R

enum class FinishStatus(val value: String, val text: Int) {
    OIL_LEAK("Oil leak", R.string.oil_leak),
    DISQUALIFIED("Disqualified", R.string.disqualified),
    ACCIDENT("Accident", R.string.accident),
    COLLISION("Collision", R.string.collision),
    ENGINE("Engine", R.string.engine),
    GEARBOX("Gearbox", R.string.gearbox),
    TRANSMISSION("Transmission", R.string.transmission),
    CLUTCH("Clutch", R.string.clutch),
    HYDRAULICS("Hydraulics", R.string.hydraulics),
    ELECTRICAL("Electrical", R.string.electrical),
    SPUN_OFF("Spun off", R.string.spun_off),
    RADIATOR("Radiator", R.string.radiator),
    SUSPENSION("Suspension", R.string.suspension),
    BRAKES("Brakes", R.string.brakes),
    DIFFERENTIAL("Differential", R.string.differential),
    OVERHEATING("Overheating", R.string.overheating),
    MECHANICAL("Mechanical", R.string.mechanical),
    TYRE("Tyre", R.string.tyre),
    DRIVER_SEAT("Driver Seat", R.string.driver_seat),
    PUNCTURE("Puncture", R.string.puncture),
    DRIVERSHAFT("Driveshaft", R.string.driveshaft)
}