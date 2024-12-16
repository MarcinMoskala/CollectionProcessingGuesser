package com.marcinmoskala.composeexercises.ui.samples.guesser.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle

fun AnnotatedString.Builder.appendFunctionCall(name: String) {
    withStyle(style = SpanStyle(color = Color(0xFF00576F))) {
        append(name)
    }
}

fun AnnotatedString.Builder.appendInt(name: String) {
    withStyle(style = SpanStyle(color = Color(0xFF1848E8))) {
        append(name)
    }
}

fun AnnotatedString.Builder.appendParameterName(name: String) {
    withStyle(style = SpanStyle(color = Color(0xFF1848E8))) {
        append(name)
    }
}

fun AnnotatedString.Builder.appendProperty(name: String) {
    withStyle(style = SpanStyle(color = Color(0xFF7C1189))) {
        append(name)
    }
}

fun AnnotatedString.Builder.appendComposableCall(name: String) {
    withStyle(style = SpanStyle(color = Color(0xFF0A920A))) {
        append(name)
    }
}