package com.solicode.realisationkutlin.model

data class TimeSlot(
    val label: String,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val ratePerHour: Double
) {
    fun getStartMinutes(): Int = startHour * 60 + startMinute
    fun getEndMinutes(): Int = endHour * 60 + endMinute
}

// Configuration des tranches horaires
val parkingTimeSlots = listOf(
    TimeSlot("Nuit", 0, 0, 7, 59, 4.0),
    TimeSlot("Jour", 8, 0, 18, 59, 8.0),
    TimeSlot("Soir", 19, 0, 23, 59, 6.0)
)