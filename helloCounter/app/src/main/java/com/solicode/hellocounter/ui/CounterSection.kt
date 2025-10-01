package com.solicode.hellocounter.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.solicode.hellocounter.R
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun CompteurSection(modifier: Modifier = Modifier) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    val decrementCd = stringResource(R.string.cd_decrement)
    val incrementCd = stringResource(R.string.cd_increment)
    val counterCd   = stringResource(R.string.cd_counter_value, count)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.title_counter),
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Bouton -
            IconButton(
                onClick = { if (count > 0) count-- },
                enabled = count > 0,
                modifier = Modifier
                    .size(56.dp) // bouton plus grand
                    .semantics { contentDescription = decrementCd }
            ) {
                Icon(
                    Icons.Filled.Remove,
                    contentDescription = null,
                    tint = if (count > 0)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }

            // Valeur du compteur
            Text(
                text = "$count",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .widthIn(min = 40.dp)
                    .semantics { contentDescription = counterCd }
            )

            // Bouton +
            IconButton(
                onClick = { count++ },
                modifier = Modifier
                    .size(56.dp)
                    .semantics { contentDescription = incrementCd }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
