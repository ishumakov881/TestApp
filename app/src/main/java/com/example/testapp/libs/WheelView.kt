//package com.lds.cinema.ui.screen.filter
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.LocalContentColor
//import androidx.compose.material3.LocalTextStyle
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.snapshotFlow
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithContent
//import androidx.compose.ui.graphics.BlendMode
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.CompositingStrategy
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.TextStyle
//import kotlinx.coroutines.flow.map
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun WheelViewWrapper(
//    state: MutableState<PickerState>,
//    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
//    startIndex: Int = 0,
//    visibleItemsCount: Int = 7,
//    textModifier: Modifier = Modifier,
//    textStyle: TextStyle = LocalTextStyle.current,
//    dividerColor: Color = LocalContentColor.current,
//) {
//
//
//    // Отслеживаем изменения в списке и прокрутке
//    LaunchedEffect(listState, state.value.items) {
//        // Если изменился список элементов, сбрасываем позицию
//        if (state.value.items.isNotEmpty()) {
//            val newStartIndex = listScrollMiddle - listScrollMiddle % state.value.items.size - visibleItemsMiddle +
//                    state.value._realItemPosition.coerceIn(0, maxOf(0, state.value.items.size - 1))
//            listState.scrollToItem(newStartIndex)
//        }
//
//        snapshotFlow { listState.firstVisibleItemIndex }
//            .map { firstVisibleIndex ->
//                val targetIndex = firstVisibleIndex + visibleItemsMiddle
//                val item = state.value.getItem(targetIndex)
//                item to targetIndex
//            }
//            .collect { (item, index) ->
//                // Обновляем состояние с новым выбранным элементом
//                val itemsSize = state.value.items.size
//                val realIndex = index % itemsSize
//
//                state.value = state.value.copy(items = state.value.items, _realItemPosition = realIndex)
//                println("{selected} Selected item at virtualPos: $index, realPos: $realIndex/$itemsSize ${state.value.selectedItem}")
//            }
//    }
//
//    state.value.getLabel(index)
//}