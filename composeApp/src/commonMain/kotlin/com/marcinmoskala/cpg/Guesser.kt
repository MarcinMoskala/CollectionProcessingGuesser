package com.marcinmoskala.composeexercises.ui.samples.guesser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.GameOver
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.GameState
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.Playing
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.Start
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.onAnswerGiven
import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.start
import com.marcinmoskala.composeexercises.ui.samples.guesser.screen.CollectionProcessingGuesserScreen
import com.marcinmoskala.composeexercises.ui.samples.guesser.screen.GameOverScreen
import com.marcinmoskala.composeexercises.ui.samples.guesser.screen.StartScreen


@Preview
@Composable
fun GuesserScreen() {
    var gameState by remember { mutableStateOf<GameState>(Start) }
    when (val state = gameState) {
        is Start -> StartScreen(
            onStart = { gameState = start() }
        )

        is Playing -> CollectionProcessingGuesserScreen(
            questionNumber = state.questionNumber,
            level = state.level,
            livesUsed = state.livesUsed,
            livesLeft = state.livesLeft,
            onAnswer = { gameState = onAnswerGiven(state, it) }
        )

        is GameOver -> GameOverScreen(
            level = state.score,
            onPlayAgain = { gameState = start() }
        )
    }
}
