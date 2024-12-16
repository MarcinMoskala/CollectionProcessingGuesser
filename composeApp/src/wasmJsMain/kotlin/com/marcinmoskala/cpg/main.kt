package com.marcinmoskala.cpg

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.marcinmoskala.composeexercises.ui.samples.guesser.GuesserScreen
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        LoadFont {
            GuesserScreen()
        }
    }
}