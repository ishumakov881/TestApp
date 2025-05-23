package com.lds.cinema.ui.screen.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ozcanalasalvar.wheelview.WheelView

// Константы для специальных значений в пикерах
private const val FROM_ALL_VALUE = "с"  // Значение для "от начала" (без нижней границы)
private const val TO_ALL_VALUE = "по"   // Значение для "до конца" (без верхней границы)

// Новая функция для диалога выбора диапазона годов
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearRangePickerDialog(
    initialFromYearIndex: Int,
    initialToYearIndex: Int,

    availableYears: List<String>,

    onDismiss: () -> Unit,
    onReset: () -> Unit,
    onConfirm: (fromYear: String, toYear: String) -> Unit
) {
    // Создаем состояния для пикеров с использованием нового подхода
    val fromYearPickerState = rememberPickerState()
    val toYearPickerState = rememberPickerState()

    // Исходные списки со специальными значениями
    val fromYearsList = listOf(FROM_ALL_VALUE) + availableYears
    val toYearsList = availableYears + listOf(TO_ALL_VALUE)

    // Вычисляем скорректированные индексы
    val adjustedFromYearIndex = if (initialFromYearIndex == 0) 0 else initialFromYearIndex + 1
    val adjustedToYearIndex =
        if (initialToYearIndex >= availableYears.size - 1) availableYears.size else initialToYearIndex

    // Инициализируем состояния с начальными данными
    val initialFromYear = fromYearsList.getOrElse(adjustedFromYearIndex) { FROM_ALL_VALUE }
    val initialToYear = toYearsList.getOrElse(adjustedToYearIndex) { TO_ALL_VALUE }


    // Установка начальных значений пикеров
    LaunchedEffect(Unit) {
        fromYearPickerState.value =
            PickerState(items = fromYearsList, _realItemPosition = adjustedFromYearIndex)
        toYearPickerState.value =
            PickerState(items = toYearsList, _realItemPosition = adjustedToYearIndex)
    }

    // Отслеживаем изменения выбора в первом пикере и обновляем второй
    LaunchedEffect(fromYearPickerState.value.selectedItem) {
        val selectedItem = fromYearPickerState.value.selectedItem
        if (selectedItem == FROM_ALL_VALUE) {
            // Все годы доступны
            toYearPickerState.value = PickerState(items = toYearsList, _realItemPosition = 2)
            //@@@ selectedItem = toYearPickerState.value.selectedItem.takeIf { it in toYearsList } ?: toYearsList.first()
        } else {
            // Ограничиваем годы в зависимости от выбранного года
            val selectedYear = selectedItem.toIntOrNull() ?: 0
            val filteredYears = availableYears.filter {
                it.toIntOrNull()?.let { year -> year > selectedYear } ?: false
            } + listOf(TO_ALL_VALUE)
            toYearPickerState.value = PickerState(items = filteredYears, _realItemPosition = 2)
        }
    }


//    LaunchedEffect(fromYearPickerState.value) {
//        println("@@@@@@@@@@@@@@@@ ${fromYearPickerState.value}")
//    }

    // Отслеживаем изменения во втором пикере для отладки
    LaunchedEffect(toYearPickerState.value.selectedItem) {
        println("[TO] Changed to: ${toYearPickerState.value.selectedItem} (${toYearPickerState.value.realIndex0()}")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Год" + "\n [F] (${fromYearPickerState.value.prettyPrint()})"
                        + "\n [T] (${toYearPickerState.value.prettyPrint()})",
                //style = MaterialTheme.typography.headlineSmall,
                style = MaterialTheme.typography.labelSmall,

                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Верхние отображения выбранных годов с надписями "с" и "по"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "с",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = fromYearPickerState.value.selectedItem,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "по",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = toYearPickerState.value.selectedItem,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Барабаны выбора годов
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Барабан для выбора года "от"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(), content = Body(fromYearPickerState)
                    )
                    // Барабан для выбора года "до"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        content = Body(toYearPickerState)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val fromYear = if (fromYearPickerState.value.selectedItem == FROM_ALL_VALUE) ""
                    else fromYearPickerState.value.selectedItem
                    val toYear = if (toYearPickerState.value.selectedItem == TO_ALL_VALUE) ""
                    else toYearPickerState.value.selectedItem
                    onConfirm(fromYear, toYear)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Выбрать")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onReset,
                enabled = fromYearPickerState.value.selectedItem != initialFromYear ||
                        toYearPickerState.value.selectedItem != initialToYear
            ) {
                Text("Сбросить")
            }
        }
    )
}

@Composable
private fun Body(state: MutableState<PickerState>): @Composable() (BoxScope.() -> Unit) =
    {


        // Градиент затемнения сверху
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0f)
                        )
                    )
                )
                .zIndex(1f)
        )

        WheelView(
            modifier = Modifier,
            itemSize = DpSize(150.dp, 25.dp),
            selection = 0,
            isEndless = state.value.items.size >= 4,
            itemCount = state.value.items.size,
            rowOffset = 2,
            onFocusItem = { item ->
                state.value = state.value.copy(_realItemPosition = item)
                state.value.selectedItem = state.value.items[item]


//                                fromYearPickerState.value = fromYearPickerState.value.copy(_realItemPosition = it).also {
//                                    it.realItemPosition = it._realItemPosition // вызовет сеттер и обновит selectedItem
//                                }

//                                fromYearPickerState.value = fromYearPickerState.value.copy(_realItemPosition = item).also {
//                                    it.selectedItem = fromYearPickerState.value.items.getOrNull(item) ?: ""
//                                }
            },
            content = {
                // Text(
                //     text = availableYears[it].toString(),
                //     textAlign = TextAlign.Start,
                //     fontSize = 17.sp,
                //     color = Color.Black
                // )

                Text(
                    text = state.value.getLabel(it),
                    maxLines = 1, textAlign = TextAlign.Start,
                    //overflow = TextOverflow.Ellipsis,
                    //style = textStyle,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.onSizeChanged {/* size -> itemHeightPixels.value = size.height*/ }
                )
            })

        // Градиент затемнения снизу
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .zIndex(1f)
        ) {
            Button(onClick = {
                state.value = state.value.copy(selectedItem = "@@@@@")

            }) {
                Text(text = "Button")
            }
        }

//                        WheelView(
//                            modifier = Modifier.fillMaxSize(),
////                            itemSize = DpSize(/*width*/, /*height*/),
//                            selection = 0,
//                            itemCount = 100,
////                            isEndless = /*isEndless*/,
////                            selectorOption = SelectorOptions(),
//                            rowOffset = 3,
//                            onFocusItem = { index ->
//                                println("@@@@ $index")
//                            },
//                            content = {

//                            })

//                        WheelView(
//                            state = fromYearPickerState,
//                            textModifier = Modifier.padding(8.dp),
//                            textStyle = TextStyle(fontSize = 32.sp),
//                            startIndex = adjustedFromYearIndex
//                        )
    }

@Preview
@Composable
private fun PreviewYearRangePickerDialog() {

    val availableYears = (1980..2024).toList().map {
        it.toString()
    }

    YearRangePickerDialog(
        initialFromYearIndex = 1900,
        initialToYearIndex = 2025,
        availableYears = availableYears,
        onDismiss = {},
        onReset = {},
        onConfirm = { fromYear: String, toYear ->
        }
    )
}