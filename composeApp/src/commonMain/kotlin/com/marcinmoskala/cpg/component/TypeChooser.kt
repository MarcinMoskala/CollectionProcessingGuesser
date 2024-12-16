package com.marcinmoskala.composeexercises.ui.samples.guesser.component

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium
import functional.Fruit
import functional.name
import functional.simpleName
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Composable
fun TypeChooser(
    questionNumber: Int,
    correctType: KType,
    types: List<KType>,
    onAnswer: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (typeChosen, setTypeChosen) = remember(questionNumber) { mutableStateOf<KType?>(null) }
    if (typeChosen == null) {
        TypeChooserSelector(
            types = types,
            onChosen = { setTypeChosen(it) },
            modifier = modifier
        )
    } else {
        if (typeChosen == correctType) {
            CorrectTypeChoice(
                correctType = correctType,
                onNext = {
                    onAnswer(typeChosen == correctType)
                },
                modifier = modifier
            )
        } else {
            IncorrectTypeChoice(
                correctType = correctType,
                typeChosen = typeChosen,
                onNext = {
                    onAnswer(typeChosen == correctType)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun IncorrectTypeChoice(
    correctType: KType,
    typeChosen: KType,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommentButton(
        comment = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                append("Incorrect! ")
            }
            append("The result is of type ")
            withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                append(correctType.simpleName)
            }
            append(" not ")
            withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                append(typeChosen.simpleName)
            }
        },
        buttonText = "Next",
        onClick = onNext,
        modifier = modifier
    )
}

@Composable
private fun CorrectTypeChoice(
    correctType: KType,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommentButton(
        comment = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold)) {
                append("Correct! ")
            }
            append("The result is of type ")
            withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                append(correctType.simpleName)
            }
        },
        buttonText = "Next",
        onClick = onNext,
        modifier = modifier
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TypeChooserSelector(
    types: List<KType>,
    onChosen: (KType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var chosenPath by remember { mutableStateOf<List<KClass<*>>>(emptyList()) }
    val classesToChooseFrom: List<KClass<*>> by remember {
        derivedStateOf {
            types.classifierToChooseFrom(chosenPath)
        }
    }
    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            "What type it the result?",
            fontSize = fontSizeMedium,
            modifier = Modifier.padding(10.dp)
        )
        FlowRow(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Center,
        ) {
            OptionsChooser(
                options = classesToChooseFrom,
                onChosen = {
                    val newPath = chosenPath + it
                    val nextChoices = types.filterByClassifierPath(newPath)
                    val singleResult = nextChoices.singleOrNull()
                    if (singleResult != null) {
                        onChosen(singleResult)
                    } else {
                        chosenPath = newPath
                    }
                },
                displayMapper = { getTypeDisplay(chosenPath + it) },
                showBackButton = chosenPath.isNotEmpty(),
                onBack = {
                    if (chosenPath.isNotEmpty()) {
                        chosenPath = chosenPath.dropLast(1)
                    }
                }
            )
        }
    }
}

private fun getTypeDisplay(displayPath: List<KClass<*>>): String {
    val path = displayPath.toMutableList()
    fun getName(classifier: KClass<*>): String {
        val name = classifier.name
        val numberOfTypeParameters = numberOfTypeParameters(classifier)
        return if (numberOfTypeParameters <= 0) name
        else "$name<${(1..numberOfTypeParameters).joinToString { path.popFirstOrNull()?.name ?: "*" }}>"
    }
    return getName(path.popFirstOrNull()!!)
}

fun numberOfTypeParameters(classifier: KClass<*>): Int = when (classifier) {
    List::class -> 1
    Set::class -> 1
    Map::class -> 2
    else -> 0
}

fun <T> MutableList<T>.popFirstOrNull(): T? = if (this.isNotEmpty()) removeAt(0) else null

private fun List<KType>.filterByClassifierPath(classifiersPath: List<KClass<*>>): List<KType> = this
    .filter {
        classifiersPath.isEmpty() ||
                it.flattenTypeArguments().take(classifiersPath.size) == classifiersPath
    }

private fun List<KType>.classifierToChooseFrom(classifiersPath: List<KClass<*>>): List<KClass<*>> =
    this
        .mapNotNull {
            val path: List<KClass<*>> = it.flattenTypeArguments()
            if (path.take(classifiersPath.size) != classifiersPath) return@mapNotNull null
            path.drop(classifiersPath.size).firstOrNull()
        }
        .distinct()

private fun KType.flattenTypeArguments(): List<KClass<*>> =
    listOf(classifier as KClass<*>) + arguments.map { it.type!!.flattenTypeArguments() }.flatten()

private val previewTypes = listOf(
    typeOf<List<Fruit>>(),
    typeOf<List<functional.Color>>(),
    typeOf<List<Int>>(),
    typeOf<List<Double>>(),
    typeOf<List<String>>(),
    typeOf<List<Boolean>>(),
    typeOf<List<List<Fruit>>>(),
    typeOf<List<List<Color>>>(),
    typeOf<Set<Fruit>>(),
    typeOf<Set<functional.Color>>(),
    typeOf<Set<Int>>(),
    typeOf<Set<Double>>(),
    typeOf<Set<String>>(),
    typeOf<Set<Boolean>>(),
    typeOf<Map<Fruit, functional.Color>>(),
    typeOf<Map<functional.Color, Fruit>>(),
    typeOf<Map<Int, Double>>(),
    typeOf<Map<Double, Int>>(),
    typeOf<Map<String, Boolean>>(),
    typeOf<Map<Boolean, String>>(),
    typeOf<Fruit>(),
    typeOf<functional.Color>(),
    typeOf<Int>(),
    typeOf<Double>(),
    typeOf<String>(),
    typeOf<Boolean>(),
)

@Preview
@Composable
fun CorrectTypeChoicePreview() {
    CorrectTypeChoice(typeOf<List<Fruit>>(), {})
}

@Preview
@Composable
fun IncorrectTypeChoicePreview() {
    IncorrectTypeChoice(typeOf<List<Fruit>>(), typeOf<List<functional.Color>>(), {})
}

@Preview
@Composable
fun TypeChooserSelectorPreview() {
    TypeChooserSelector(
        types = previewTypes,
        onChosen = {
            println("Chosen: $it")
        }
    )
}

@Preview
@Composable
fun TypeChooserPreview() {
    TypeChooser(
        questionNumber = 1,
        correctType = typeOf<List<Fruit>>(),
        types = previewTypes,
        onAnswer = {
            println("Answer: $it")
        }
    )
}