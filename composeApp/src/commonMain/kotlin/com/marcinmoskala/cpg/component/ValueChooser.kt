package com.marcinmoskala.composeexercises.ui.samples.guesser.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeBig
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium
import functional.Fruit
import functional.allFruits
import functional.simpleName
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Composable
fun ValueChooser(
    questionNumber: Int,
    result: Any,
    resultType: KType,
    fruits: Set<Fruit>,
    onAnswer: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var resultChosen by remember(questionNumber) { mutableStateOf<Any?>(null) }
    if (resultChosen == null) {
        Box(modifier = modifier) {
            when (resultType) {
                typeOf<String>() -> TextAnswer { resultChosen = it }
                typeOf<Double>() -> DoubleAnswer { resultChosen = it }
                typeOf<Int>() -> IntAnswer { resultChosen = it }
                typeOf<Fruit>() -> FruitChooser(fruits.toList()) { resultChosen = it }
                typeOf<functional.Color>() -> FruitColorChooser { resultChosen = it }
                typeOf<Boolean>() -> BooleanChooser { resultChosen = it }
                typeOf<List<Fruit>>() -> ListChooser(fruits.toList()) { resultChosen = it }
                typeOf<List<functional.Color>>() -> ListChooser(functional.Color.entries) {
                    resultChosen = it
                }

                typeOf<List<Boolean>>() -> ListChooser(listOf(true, false)) { resultChosen = it }
                typeOf<Set<Fruit>>() -> ListChooser(fruits.toList()) { resultChosen = it.toSet() }
                typeOf<Set<functional.Color>>() -> ListChooser(functional.Color.entries) {
                    resultChosen = it.toSet()
                }

                typeOf<Set<Boolean>>() -> ListChooser(listOf(true, false)) {
                    resultChosen = it.toSet()
                }

                else -> Text("Unsupported type: $resultType")
            }
        }
    } else {
        if (resultChosen == result) {
            CommentButton(
                comment = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold)) {
                        append("Correct! ")
                    }
                    append("The result is ")
                    withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                        append(result.toString())
                    }
                },
                buttonText = "Continue",
                onClick = { onAnswer(true) },
                modifier = modifier,
            )
        } else {
            CommentButton(
                comment = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                        append("Incorrect! ")
                    }
                    append("The result is ")
                    withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                        append(result.toString())
                    }
                    append(" not ")
                    withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                        append(resultChosen.toString())
                    }
                },
                buttonText = "Continue",
                onClick = { onAnswer(false) },
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
fun FruitColorChooser(onChosen: (functional.Color) -> Unit = {}) {
    OptionsChooser(functional.Color.entries, onChosen)
}

@Preview
@Composable
fun FruitChooser(fruits: List<Fruit> = allFruits, onChosen: (Fruit) -> Unit = {}) {
    OptionsChooser(fruits, onChosen)
}

@Preview
@Composable
fun BooleanChooser(onChosen: (Boolean) -> Unit = {}) {
    OptionsChooser(listOf(true, false), onChosen)
}

@Preview
@Composable
fun TextAnswer(onChosen: (String) -> Unit = {}) {
    val (text, setText) = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = setText,
            label = { Text("What string is it?") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onChosen(text) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        ) {
            // Right arrow icon
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun IntAnswer(onChosen: (Int) -> Unit = {}) {
    val (text, setText) = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = { value: String ->
                setText(value.filter { it.isDigit() })
            },
            label = { Text("What number is it?") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            enabled = text.toIntOrNull() != null,
            onClick = { onChosen(text.toInt()) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun DoubleAnswer(onChosen: (Double) -> Unit = {}) {
    val (text, setText) = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = { value: String ->
                setText(value.filter { it.isDigit() || it == '.' })
            },
            label = { Text("What number is it?") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            enabled = text.toDoubleOrNull() != null,
            onClick = { onChosen(text.toDouble()) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun <T> ListChooser(values: List<T>, onChosen: (List<T>) -> Unit = {}) {
    var chosen: List<T> by remember { mutableStateOf(emptyList()) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            "Chosen values:",
            fontSize = fontSizeMedium,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            "$chosen",
            fontSize = fontSizeBig,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(5.dp)
        )
        OptionsChooser(
            values,
            onChosen = { chosen += it },
            showBackButton = chosen.isNotEmpty(),
            onBack = { chosen = chosen.dropLast(1) },
            modifier = Modifier.padding(5.dp),
        )
        Button(onClick = {
            onChosen(chosen)
        }) {
            Row {
                Text(
                    "Submit",
                    fontSize = fontSizeMedium,
                )
                Image(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.padding(start = 5.dp, top = 2.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewListChooser() {
    ListChooser(listOf(1, 2, 3))
}