package com.solicode.hellocounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.solicode.hellocounter.ui.MainScreen
import com.solicode.hellocounter.ui.theme.HelloCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloCounterTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewMainScreen() {
    HelloCounterTheme {
        MainScreen()
    }
}
