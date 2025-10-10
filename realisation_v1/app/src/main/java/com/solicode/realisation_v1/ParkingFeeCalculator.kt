package com.solicode.parkingfee

data class PerTranche(val label: String, val minutes: Int, val cost: Double)

data class CalculationResult(
    val perTranche: List<PerTranche>,
    val totalMinutes: Int,
    val totalAmount: Double,
    val error: String? = null
) {
    fun summary(): String {
        val details = perTranche.joinToString(" | ") { "${it.label}: ${String.format("%.2f MAD", it.cost)}" }
        return "Total ${String.format("%.2f MAD", totalAmount)} → $details"
    }
}

fun calculateParkingFee(startStr: String, endStr: String, hourStarted: Boolean = false): CalculationResult {
    fun parseTime(t: String): Int? {
        val parts = t.split(":")
        if (parts.size != 2) return null
        val h = parts[0].toIntOrNull() ?: return null
        val m = parts[1].toIntOrNull() ?: return null
        if (h !in 0..23 || m !in 0..59) return null
        return h * 60 + m
    }

    val start = parseTime(startStr) ?: return CalculationResult(emptyList(), 0, 0.0, "Heure début invalide")
    val end = parseTime(endStr) ?: return CalculationResult(emptyList(), 0, 0.0, "Heure fin invalide")
    val totalEnd = if (end < start) end + 24*60 else end

    val tranches = listOf(
        Triple("Nuit", 0, 7*60 + 59) to 4.0,
        Triple("Jour", 8*60, 18*60 + 59) to 8.0,
        Triple("Soir", 19*60, 23*60 + 59) to 6.0
    )

    val perTranche = mutableListOf<PerTranche>()
    var totalMinutes = 0
    var totalAmount = 0.0

    fun overlap(s1: Int, e1: Int, s2: Int, e2: Int): Int {
        val startOverlap = maxOf(s1, s2)
        val endOverlap = minOf(e1, e2)
        return if (endOverlap > startOverlap) endOverlap - startOverlap else 0
    }

    for ((triple, rate) in tranches) {
        val label = triple.first
        val trancheStart = triple.second
        val trancheEnd = triple.third
        val adjustedEnd = if (trancheEnd < trancheStart) trancheEnd + 24*60 else trancheEnd
        val minutes = overlap(start, totalEnd, trancheStart, adjustedEnd)
        if (minutes > 0) {
            val cost = if (hourStarted) ((minutes + 59)/60.0) * rate else minutes/60.0 * rate
            perTranche.add(PerTranche(label, minutes, cost))
            totalMinutes += minutes
            totalAmount += cost
        }
    }

    return CalculationResult(perTranche, totalMinutes, totalAmount)
}
12