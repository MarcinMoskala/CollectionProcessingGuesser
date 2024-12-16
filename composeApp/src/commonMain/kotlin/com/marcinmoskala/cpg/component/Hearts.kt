package com.marcinmoskala.composeexercises.ui.samples.guesser.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import collectionprocessingguesser.composeapp.generated.resources.Res
import collectionprocessingguesser.composeapp.generated.resources.heart_empty
import collectionprocessingguesser.composeapp.generated.resources.heart_full
import org.jetbrains.compose.resources.painterResource

@Preview
@Composable
fun Hearts(used: Int = 1, left: Int = 2, modifier: Modifier = Modifier) {
    val imageModifier = Modifier
        .size(80.dp)
        .padding(20.dp)
    Row(modifier = modifier.padding(20.dp)) {
        repeat(used) {
            Image(
                painter = painterResource(Res.drawable.heart_empty),
                contentDescription = "Heart used",
                modifier = imageModifier
            )
        }
        repeat(left) {
            Image(
                painter = painterResource(Res.drawable.heart_full),
                contentDescription = "Heart left",
                modifier = imageModifier
            )
        }
    }
}