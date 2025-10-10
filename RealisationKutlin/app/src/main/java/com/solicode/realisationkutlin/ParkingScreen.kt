package com.solicode.realisationkutlin

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

data class Tranche(val label: String, val start: String, val end: String, val rate: Double)

val historyList = mutableListOf<String>()

@RequiresApi(Build.VERSION_CODES.O)
fun calculateParkingFee(
    startTime: String,
    endTime: String,
    tranches: List<Tranche>,
    roundingMode: String
): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val start = LocalTime.parse(startTime, formatter)
    val end = LocalTime.parse(endTime, formatter)

    var total = 0.0
    val details = mutableListOf<String>()

    for (t in tranches) {
        val trancheStart = LocalTime.parse(t.start, formatter)
        val trancheEnd = LocalTime.parse(t.end, formatter)

        var minutes = 0L

        if (end.isBefore(start)) {
            // avant minuit
            if (trancheStart.isBefore(start)) {
                val overlapStart = if (start.isAfter(trancheStart)) start else trancheStart
                val overlapEnd = trancheEnd
                if (overlapEnd.isAfter(overlapStart)) {
                    minutes += java.time.Duration.between(overlapStart, overlapEnd).toMinutes()
                }
            }
            // après minuit
            if (trancheEnd.isAfter(end) || trancheEnd == end) {
                val overlapStart = trancheStart
                val overlapEnd = if (end.isBefore(trancheEnd)) end else trancheEnd
                if (overlapEnd.isAfter(overlapStart)) {
                    minutes += java.time.Duration.between(overlapStart, overlapEnd).toMinutes()
                }
            }
        } else {
            val overlapStart = if (start.isAfter(trancheStart)) start else trancheStart
            val overlapEnd = if (end.isBefore(trancheEnd)) end else trancheEnd
            if (overlapEnd.isAfter(overlapStart)) {
                minutes = java.time.Duration.between(overlapStart, overlapEnd).toMinutes()
            }
        }

        if (minutes > 0) {
            val hours = when (roundingMode) {
                "proportionnel" -> minutes / 60.0
                "heure" -> ceil(minutes / 60.0)
                else -> minutes / 60.0
            }
            val amount = hours * t.rate
            details.add("${t.label}: $minutes min → ${"%.2f".format(amount)} MAD")
            total += amount
        }
    }


    val result = details.joinToString("\n") + "\nTotal: ${"%.2f".format(total)} MAD"

    historyList.add(result)
    if (historyList.size > 3) {
        historyList.removeAt(0)
    }

    return result
}

fun getHistory(): List<String> {
    return historyList.reversed()
}