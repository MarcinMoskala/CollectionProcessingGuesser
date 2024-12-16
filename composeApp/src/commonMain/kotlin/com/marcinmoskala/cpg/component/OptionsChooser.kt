package com.marcinmoskala.composeexercises.ui.samples.guesser.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> OptionsChooser(
    options: List<T>,
    onChosen: (T) -> Unit,
    displayMapper: (T) -> String = { it.toString() },
    showBackButton: Boolean = false,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        options.forEach { option ->
            Button(
                onClick = { onChosen(option) },
                modifier = Modifier.padding(5.dp)
                    .focusable()
                    .onKeyEvent {
                        val isEnter = it.key == Key.Enter
                        if (isEnter) {
                            onChosen(option)
                        }
                        isEnter
                    },
            ) {
                Text(
                    displayMapper(option),
                    fontFamily = FontFamily.Monospace,
                    fontSize = fontSizeMedium,
                )
            }
        }
        if (showBackButton) {
            Button(
                onClick = requireNotNull(onBack) { "onBack must be provided when showBackButton is true" },
                colors = buttonColors(backgroundColor = Color.Red),
                modifier = Modifier.padding(5.dp)
                    .focusable()
                    .onKeyEvent {
                        val isEnter = it.key == Key.Enter
                        if (isEnter) {
                            onBack()
                        }
                        isEnter
                    },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    }
}

@Preview
@Composable
fun OptionsChooserPreview() {
    OptionsChooser(
        options = listOf("A", "B"),
        onChosen = {},
        showBackButton = true,
        onBack = {},
        modifier = Modifier.fillMaxWidth()
    )
}