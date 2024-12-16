package com.marcinmoskala.composeexercises.ui.samples.guesser.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marcinmoskala.composeexercises.ui.samples.guesser.component.Code
import com.marcinmoskala.composeexercises.ui.samples.guesser.component.FruitPropertiesTable
import com.marcinmoskala.composeexercises.ui.samples.guesser.component.Hearts
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.Level
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium
import com.marcinmoskala.cpg.component.ResultChooser
import functional.CollectionProcessingChallenge
import functional.generateCollectionProcessingChallenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CollectionProcessingGuesserScreen(
    questionNumber: Int,
    level: Level,
    livesUsed: Int,
    livesLeft: Int,
    onAnswer: (Boolean) -> Unit = {},
) {
    val (game, setGame) = remember(questionNumber) {
        mutableStateOf<CollectionProcessingChallenge?>(
            null
        )
    }
    LaunchedEffect(questionNumber) {
        launch(Dispatchers.Default) {
            setGame(generateCollectionProcessingChallenge(level))
        }
    }
    if (game == null) {
        Loading()
    } else {
        CollectionProcessingGuesser(
            challenge = game,
            questionNumber = questionNumber,
            level = level,
            livesUsed = livesUsed,
            livesLeft = livesLeft,
            onAnswer = onAnswer,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CollectionProcessingGuesser(
    challenge: CollectionProcessingChallenge,
    questionNumber: Int,
    level: Level,
    livesUsed: Int,
    livesLeft: Int,
    onAnswer: (Boolean) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Hearts(livesUsed, livesLeft)
            Text(
                text = "Level: ${level.value}",
                fontSize = fontSizeMedium,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp),
        ) {
            if (challenge.fruitsUsed.isNotEmpty() && challenge.fruitPropertiesUsed.isNotEmpty()) {
                FruitPropertiesTable(
                    challenge.fruitsUsed,
                    challenge.fruitPropertiesUsed
                )
            }
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Code(challenge.toDisplayString())
            }
        }
        ResultChooser(
            questionNumber = questionNumber,
            fruits = challenge.fruitsUsed,
            result = challenge.result,
            resultType = challenge.resultType,
            onAnswer = onAnswer,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}

@Preview
@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp)
        )
    }
}