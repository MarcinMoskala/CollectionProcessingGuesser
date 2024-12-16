package com.marcinmoskala.composeexercises.ui.samples.guesser.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcinmoskala.composeexercises.ui.samples.guesser.fontSizeMedium
import functional.Fruit
import functional.allFruits
import kotlin.reflect.KProperty1

@Composable
fun FruitPropertiesTable(fruitsUsed: Set<Fruit>, fruitPropertiesUsed: List<KProperty1<Fruit, *>>) {
    Row(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Column {
            ColumnCell("Fruit")
            fruitsUsed.forEach { ColumnCell(it.toString()) }
        }
        fruitPropertiesUsed.forEach { property ->
            Column {
                ColumnCell(property.name.capitalize())
                fruitsUsed.forEach { ColumnCell(property.getValue(it)) }
            }
        }
    }
}

fun KProperty1<Fruit, *>.getValue(fruit: Fruit): String = when (this) {
    Fruit::color -> fruit.color.name
    Fruit::name -> fruit.name
    Fruit::price -> fruit.price.toString()
    else -> runCatching {
        get(fruit).toString()
    }.getOrElse {
        "Incorrect property $this"
    }
}

@Composable
fun ColumnCell(text: String) {
    Text(
        text,
        fontSize = fontSizeMedium,
        modifier = Modifier.padding(5.dp),
    )
}

fun String.capitalize() =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

@Preview
@Composable
fun FruitPropertiesTablePreview() {
    FruitPropertiesTable(
        fruitsUsed = allFruits.take(10).toSet(),
        fruitPropertiesUsed = listOf(Fruit::color, Fruit::name, Fruit::price)
    )
}