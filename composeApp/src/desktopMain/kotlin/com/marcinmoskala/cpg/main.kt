package com.marcinmoskala.cpg

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.marcinmoskala.composeexercises.ui.samples.guesser.GuesserScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CollectionProcessingGuesser",
    ) {
        GuesserScreen()
    }
}