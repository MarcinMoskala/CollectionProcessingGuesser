package com.marcinmoskala.cpg.data

import com.marcinmoskala.composeexercises.ui.samples.guesser.domain.Level
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

fun randomChallenge(level: Level) {
    val difficulty = when(level.value) {
        in 0..5 -> Difficulty.L1
        in 6..10 -> Difficulty.L2
        in 11..15 -> Difficulty.L3
        in 16..20 -> Difficulty.L4
        in 21..30 -> Difficulty.L5
        in 31..40 -> Difficulty.L6
        in 41..50 -> Difficulty.L7
        in 51..65 -> Difficulty.L8
        in 66..80 -> Difficulty.L9
        else -> Difficulty.L10
    }
}

data class Challenge(
    val displayString: String,
    val resultType: ResultType,
    val result: Any,
    val fruitsUsed: List<String>,
    val fruitPropertiesUsed: List<String>,
)

enum class ResultType {
    ListFruit,
    ListColor,
    ListInt,
    ListDouble,
    ListString,
    ListBoolean,
    SetFruit,
    SetColor,
    SetInt,
    SetDouble,
    SetString,
    SetBoolean,
    Fruit,
    Color,
    Int,
    Double,
    String,
    Boolean,
}

enum class Difficulty(val fruitsNum: Int, val stepsNum: Int) {
    L1(5, 3),
    L2(6, 4),
    L3(7, 5),
    L4(8, 6),
    L5(9, 7),
    L6(10, 8),
    L7(12, 8),
    L8(14, 8),
    L9(17, 8),
    L10(20, 8),
}
