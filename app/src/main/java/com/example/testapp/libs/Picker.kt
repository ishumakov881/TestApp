package com.lds.cinema.ui.screen.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    state: MutableState<PickerState>,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 7,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    dividerColor: Color = LocalContentColor.current,
) {
    val visibleItemsMiddle = visibleItemsCount / 2


    // "Бесконечный" скролл
    val listScrollCount = 60//Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2

    // Проверяем, есть ли элементы в списке
    val itemsSize = state.value.items.size

    if (itemsSize == 0) {
        // Если список пуст, ничего не показываем
        return
    }

    // Вычисляем начальный индекс для списка
    val listStartIndex = listScrollMiddle - listScrollMiddle % itemsSize - visibleItemsMiddle +
            startIndex.coerceIn(0, maxOf(0, itemsSize - 1))



    // Создаем состояние для LazyColumn
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Хранение высоты элемента для рассчетов
    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    // Градиент для эффекта затухания элементов по краям
    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    // Отслеживаем изменения в списке и прокрутке
    LaunchedEffect(listState, state.value.items) {
        // Если изменился список элементов, сбрасываем позицию
        if (state.value.items.isNotEmpty()) {
            val newStartIndex = listScrollMiddle - listScrollMiddle % state.value.items.size - visibleItemsMiddle +
                    state.value._realItemPosition.coerceIn(0, maxOf(0, state.value.items.size - 1))
            listState.scrollToItem(newStartIndex)
        }

        snapshotFlow { listState.firstVisibleItemIndex }
            .map { firstVisibleIndex ->
                val targetIndex = firstVisibleIndex + visibleItemsMiddle
                val item = state.value.getItem(targetIndex)
                item to targetIndex
            }
            .collect { (item, index) ->
                // Обновляем состояние с новым выбранным элементом
                val itemsSize = state.value.items.size
                val realIndex = index % itemsSize
                
                state.value = state.value.copy(items = state.value.items, _realItemPosition = realIndex)
                println("{selected} Selected item at virtualPos: $index, realPos: $realIndex/$itemsSize ${state.value.selectedItem}")
            }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                Text(
                    text = state.value.getLabel(index),
                    maxLines = 1,
                    //overflow = TextOverflow.Ellipsis,
                    //style = textStyle,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .then(textModifier)
                )
            }
        }

        // Разделители для выделения выбранного элемента
        HorizontalDivider(
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle),
            color = dividerColor
        )

        HorizontalDivider(
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1)),
            color = dividerColor
        )
    }
}

// Вспомогательная функция для эффекта затухания по краям
private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }