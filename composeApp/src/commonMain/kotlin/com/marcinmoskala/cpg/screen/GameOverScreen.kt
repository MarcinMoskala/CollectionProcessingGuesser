package com.marcinmoskala.composeexercises.ui.samples.guesser.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.Level
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeBig
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeSmall

@Preview
@Composable
fun GameOverScreen(
    level: Level = Level(10),
    onPlayAgain: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Game over! ",
            textAlign = TextAlign.Center,
            fontSize = fontSizeBig,
            modifier = Modifier.padding(20.dp),
        )
        Text(
            "You reached level ${level.value}.",
            textAlign = TextAlign.Center,
            fontSize = fontSizeMedium,
            modifier = Modifier.padding(20.dp),
        )
        Button(onPlayAgain) {
            Text(
                "Play again",
                fontSize = fontSizeMedium,
            )
        }
        Text(
            buildAnnotatedString {
                val hyperlinkStyle = SpanStyle(
                    color = Color(0xff64B5F6),
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
                append("Made with ❤\uFE0F\nby ")
                withLink(LinkAnnotation.Url(url = "https://kt.academy/user/marcinmoskala")) {
                    withStyle(style = hyperlinkStyle) {
                        append("Marcin Moskała")
                    }
                }
                append("\nin ")
                withLink(LinkAnnotation.Url(url = "https://www.jetbrains.com/compose-multiplatform/")) {
                    withStyle(style = hyperlinkStyle) {
                        append("Compose Multiplatform")
                    }
                }
                append(".\n\nIf you want to improve your compose skills,\ncheck out ")
                withLink(LinkAnnotation.Url(url = "https://kt.academy/#workshops-offer")) {
                    withStyle(style = hyperlinkStyle) {
                        append("my workshops")
                    }
                }
                append(".")
            },
            textAlign = TextAlign.Center,
            fontSize = fontSizeSmall,
            modifier = Modifier.padding(20.dp),
        )
    }
}