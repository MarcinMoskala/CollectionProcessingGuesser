package com.marcinmoskala.cpg.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.marcinmoskala.composeexercises.ui.samples.guesser.component.TypeChooser
import com.marcinmoskala.composeexercises.ui.samples.guesser.component.ValueChooser
import functional.Fruit
import functional.supportedTypes
import kotlin.reflect.KType

@Composable
fun ResultChooser(
    questionNumber: Int,
    result: Any,
    resultType: KType,
    fruits: Set<Fruit>,
    onAnswer: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var typeChosen by remember(questionNumber) { mutableStateOf(false) }

    if (!typeChosen) {
        TypeChooser(
            questionNumber = questionNumber,
            correctType = resultType,
            types = supportedTypes.toList(),
            onAnswer = { correct ->
                if (correct) {
                    typeChosen = true
                } else {
                    onAnswer(false)
                }
            },
            modifier = modifier,
        )
    } else {
        ValueChooser(
            questionNumber = questionNumber,
            result = result,
            resultType = resultType,
            fruits = fruits,
            onAnswer = onAnswer,
            modifier = modifier,
        )
    }
}

