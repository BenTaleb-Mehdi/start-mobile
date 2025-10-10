package com.solicode.realisationkutlin


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ParkingFeeApp() {
    var startTime by remember { mutableStateOf("00:00") }
    var endTime by remember { mutableStateOf("00:00") }
    var roundingMode by remember { mutableStateOf("proportionnel") }
    var result by remember { mutableStateOf("") }

    // Fixed tranches as per your example
    val tranches = listOf(
        Tranche("Nuit", "00:00", "07:59", 4.0),
        Tranche("Jour", "08:00", "18:59", 8.0),
        Tranche("Soir", "19:00", "23:59", 6.0)
    )

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = startTime,
            onValueChange = { startTime = it },
            label = { Text("Start HH:mm") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = endTime,
            onValueChange = { endTime = it },
            label = { Text("End HH:mm") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row {
            Button(onClick = { roundingMode = "proportionnel"; result = calculateParkingFee(startTime, endTime, tranches, roundingMode) }) {
                Text("Proportionnel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { roundingMode = "heure"; result = calculateParkingFee(startTime, endTime, tranches, roundingMode) }) {
                Text("Heure entamée")
            }
        }
        Text(result)
    }
}
