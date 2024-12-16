package com.marcinmoskala.composeexercises.ui.samples.guesser.domain

sealed interface GameState
data object Start : GameState
data class Playing(
    val level: Level,
    val questionNumber: Int,
    val livesUsed: Int,
    val livesLeft: Int,
) : GameState
data class GameOver(val score: Level) : GameState

class Level(val value: Int) {
    operator fun plus(i: Int) = Level(value + i)
    val fruitsNum get() = minOf(3 + value / 4, 8)
    val stepsNum get() = minOf(1 + (value + 2) / 4, 14)
}

fun start(): GameState = Playing(
    level = Level(1),
    questionNumber = 1,
    livesUsed = 0,
    livesLeft = 3,
)

fun onAnswerGiven(state: Playing, answerCorrect: Boolean): GameState {
    val livesLeft = state.livesLeft - if (answerCorrect) 0 else 1
    val livesUsed = state.livesUsed + if (answerCorrect) 0 else 1
    if (livesLeft <= 0) return GameOver(state.level)

    return Playing(
        level = state.level + if(answerCorrect) 1 else 0,
        livesUsed = livesUsed,
        livesLeft = livesLeft,
        questionNumber = state.questionNumber + 1,
    )
}