package com.marcinmoskala.composeexercises.ui.samples.guesser.component

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium
import functional.simpleName
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CommentButton(
    comment: AnnotatedString,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            comment,
            fontSize = fontSizeMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
        Button(
            onClick = onClick,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                buttonText,
                fontSize = fontSizeMedium,
            )
        }
    }
}

@Preview
@Composable
fun CommentButtonPreview() {
    CommentButton(
        comment = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                append("Incorrect! ")
            }
            append("The result is of type ")
            withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                append("Int")
            }
            append(" not ")
            withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                append("String")
            }
        },
        buttonText = "Continue",
        onClick = { }
    )
}