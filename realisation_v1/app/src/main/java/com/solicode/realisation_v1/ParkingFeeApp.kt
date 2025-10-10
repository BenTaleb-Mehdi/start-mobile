package com.solicode.parkingfee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingFeeApp() {
    var start by rememberSaveable { mutableStateOf("23:10") } // exemple
    var end by rememberSaveable { mutableStateOf("00:40") }   // exemple
    var roundingMode by rememberSaveable { mutableStateOf("hour_started") }

    var lastResults by rememberSaveable { mutableStateOf(listOf<String>()) }
    var currentResult by rememberSaveable { mutableStateOf<CalculationResult?>(null) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ParkingFee — Tarif par tranches horaires", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = start,
            onValueChange = { start = it },
            label = { Text("Heure début (HH:MM)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = end,
            onValueChange = { end = it },
            label = { Text("Heure fin (HH:MM)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Text("Mode d'arrondi :", style = MaterialTheme.typography.titleSmall)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = roundingMode == "proportional", onClick = { roundingMode = "proportional" })
            Text("Proportionnel (par minute)", modifier = Modifier.padding(end = 12.dp))
            RadioButton(selected = roundingMode == "hour_started", onClick = { roundingMode = "hour_started" })
            Text("Heure entamée par tranche")
        }

        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                val res = calculateParkingFee(start, end, roundingMode == "hour_started")
                if (res.error != null) {
                    errorMsg = res.error
                    currentResult = null
                } else {
                    errorMsg = null
                    currentResult = res
                }
            }) { Text("Calculer") }

            Button(onClick = {
                start = ""
                end = ""
                currentResult = null
                errorMsg = null
            }) { Text("Réinitialiser") }
        }

        Spacer(Modifier.height(12.dp))

        errorMsg?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        currentResult?.let { res ->
            Text("Durée totale: ${res.totalMinutes} min", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Text("Détail par tranche :", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(6.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp)) {
                itemsIndexed(res.perTranche) { _, part ->
                    val color = when(part.label) {
                        "Nuit" -> Color(0xFF1E1E1E)
                        "Jour" -> Color(0xFFFFF176)
                        "Soir" -> Color(0xFF90CAF9)
                        else -> Color.LightGray
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${part.label} (${part.minutes} min)")
                        Text(String.format("%.2f MAD", part.cost))
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Total: ${String.format("%.2f MAD", res.totalAmount)}", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            Row {
                Button(onClick = {
                    val summary = res.summary()
                    lastResults = (listOf(summary) + lastResults).take(3)
                }) { Text("Enregistrer (mémoire 3 derniers)") }

                Spacer(Modifier.width(8.dp))

                Button(onClick = { lastResults = emptyList() }) { Text("Effacer historique") }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Historique (3 derniers) :", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(6.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp)) {
            itemsIndexed(lastResults) { index, item ->
                Text("${index + 1}. $item")
            }
        }
    }
}
