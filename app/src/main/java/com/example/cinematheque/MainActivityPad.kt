package com.example.cinematheque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.berggren.ScrollableGrid
import dev.berggren.dpadFocusable0

class MainActivityPad : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rowColors = listOf(
            Color(0xff1abc9c),
            Color(0xff2ecc71),
            Color(0xff3498db),
            Color(0xff9b59b6),
            Color(0xff34495e)
        )
        val itemsPerRow = 10
        val boxColors = rowColors.map { rowColor ->
            (0..itemsPerRow).map { rowIndex ->
                val fraction = (1 - rowIndex.toFloat() / itemsPerRow)
                Color(
                    red = fraction * rowColor.red,
                    green = fraction * rowColor.green,
                    blue = fraction * rowColor.blue
                )
            }
        }
        setContent {
            var colorClicked: Color by remember { mutableStateOf(Color.Transparent) }

            MaterialTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xffecf0f1))
                        .padding(top = boxPadding)
                ) {
                    ColorClickedBanner(color = colorClicked)
                    Spacer(Modifier.height(boxPadding))
                    ScrollableGrid(
                        items = boxColors
                    ) { color, position ->
                        val elementPaddingAndHalfOfNextBox = with(LocalDensity.current) {
                            (boxPadding + boxSize.div(2)).toPx()
                        }
                        ColoredBox(
                            Modifier.dpadFocusable0(
                                onClick = {
                                    colorClicked = color
                                },
                                scrollPadding = Rect(
                                    left = elementPaddingAndHalfOfNextBox,
                                    top = elementPaddingAndHalfOfNextBox,
                                    right = elementPaddingAndHalfOfNextBox,
                                    bottom = elementPaddingAndHalfOfNextBox
                                ),
                                isDefault = position.rowIndex == 0 && position.columnIndex == 0
                            ),
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorClickedBanner(color: Color) {
    Row {
        Row(
            Modifier
                .height(IntrinsicSize.Min)
                .padding(start = boxPadding)
        ) {
            Text(text = "Clicked color: ", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.width(24.dp))
            Box(
                Modifier
                    .background(color, CircleShape)
                    .aspectRatio(1f)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun ColoredBox(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier
            .size(boxSize)
            .background(color)
    )
}

private val boxSize = 128.dp
private val boxPadding = 24.dp
