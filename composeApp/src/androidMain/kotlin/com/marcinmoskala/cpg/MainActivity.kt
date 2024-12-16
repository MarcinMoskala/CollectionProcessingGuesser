package com.marcinmoskala.cpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.marcinmoskala.composeexercises.ui.samples.guesser.GuesserScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GuesserScreen()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    GuesserScreen()
}