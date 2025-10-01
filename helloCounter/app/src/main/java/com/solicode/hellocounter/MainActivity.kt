package com.solicode.hellocounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.solicode.hellocounter.ui.MainScreen  // ← bien importer MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()  // ← ton écran principal avec les deux sections
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewMainScreen() {
    MaterialTheme {
        MainScreen()
    }
}
