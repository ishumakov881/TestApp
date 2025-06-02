package com.example.cinematheque

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.Default
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovieGridScreenSimple(initialFocusRequester: FocusRequester) {
    val rows = 4
    val cols = 4
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    var isFirstItemFocused by remember { mutableStateOf(false) }

    val focusManager: FocusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        repeat(rows) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .focusRestorer(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(cols) { col ->


                    //val interactionSource = remember { MutableInteractionSource() }
                    //val isFocused = interactionSource.collectIsFocusedAsState().value
                    var isFocused by remember { mutableStateOf(false) }

                    val focusModifier = Modifier
                        .size(160.dp)
                        .padding(4.dp)
                        //.focusable(interactionSource = interactionSource)

                        //РАБОТАЕТ ТОЛЬКО ТАК
                        .focusRequester(if (row == 0 && col == 0) initialFocusRequester else Default)
                        .onFocusChanged {
                            isFocused = it.isFocused
                            if (it.isFocused) {
                                println("Focused: [$row][$col]")
                            }
                        }

                        .focusProperties {
                            // Запрещаем выход за границы сетки
                            if (col == 0) left = FocusRequester.Cancel
                            if (col == cols - 1) right = FocusRequester.Cancel
                            if (row == 0) up = FocusRequester.Cancel
                            if (row == rows - 1) down = FocusRequester.Cancel

                            exit = { FocusRequester.Cancel }

                            canFocus = true

                        }
                        .clickable {
                            println("Clicked: [$row][$col]")


                            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()

                            //if (row != 0 && col != 0) {

                            coroutineScope.launch {
                                delay(1000)
                                focusManager.clearFocus(true)
                                delay(300)
                                initialFocusRequester.requestFocus()

                                delay(500)
                                focusManager.moveFocus(FocusDirection.Down) //<<--- Этот код коректно переводит
                            }
                            //}
                        }

//                    ЭТО НЕ РАБОТАЕТ
//                        .then(
//                            Modifier.focusRequester(if (row == 0 && col == 0) initialFocusRequester else Default)
//                        )
//                        ЭТО НЕ РАБОТАЕТ
////                        .then(
////                            if (row == 0 && col == 0)
////                                Modifier.focusRequester(initialFocusRequester)
////                            else Modifier.focusRequester(Default)
////                        )
                        .focusable()
                    Card(
                        modifier = focusModifier,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isFocused) 8.dp else 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isFocused) Color(0xFFE3F2FD) else Color.White
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            if (isFocused) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .border(
                                            width = 2.dp,
                                            color = Color.Red,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                )
                            }
                            Text(
                                "@@@ $row x $col",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }

    // Запрашиваем фокус для первого элемента после первого composition
    LaunchedEffect(Unit) {
        if (!isFirstItemFocused) {
            //initialFocusRequester.requestFocus()
            isFirstItemFocused = true
        }
    }
}