package com.marcinmoskala.composeexercises.ui.samples.guesser.domain

import com.marcinmoskala.composeexercises.ui.samples.guesser.trackEvent

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
    val fruitsNum get() = minOf(3 + value / 4, 14)
    val stepsNum get() = minOf(1 + (value + 2) / 4, 8)
}

fun start(): GameState {
    // level_start tracked in UI to avoid double tracking on replay
    return Playing(
        level = Level(1),
        questionNumber = 1,
        livesUsed = 0,
        livesLeft = 3,
    )
}

fun onAnswerGiven(state: Playing, answerCorrect: Boolean): GameState {
    trackEvent(
        "answer_given", mapOf(
            "level" to state.level.value.toString(),
            "correct" to answerCorrect.toString()
        )
    )
    val livesLeft = state.livesLeft - if (answerCorrect) 0 else 1
    val livesUsed = state.livesUsed + if (answerCorrect) 0 else 1
    if (livesLeft <= 0) {
        trackEvent("game_over", mapOf("level" to state.level.value.toString()))
        return GameOver(state.level)
    }

    val nextLevel = state.level + if (answerCorrect) 1 else 0
    if (answerCorrect) {
        trackEvent("level_start", mapOf("level" to nextLevel.value.toString()))
    }

    return Playing(
        level = nextLevel,
        livesUsed = livesUsed,
        livesLeft = livesLeft,
        questionNumber = state.questionNumber + 1,
    )
}