package com.lds.cinema.ui.screen.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberPickerState(): MutableState<PickerState> = remember { mutableStateOf(PickerState()) }

data class PickerState(
    val items: List<String> = emptyList(),
) {
    var realItemPosition: Int = -999
    var virtualItemPosition: Int = -999

    val selectedItem: String
        get() = (items.getOrNull(realItemPosition) ?: "")

    fun prettyPrint(): String {
        val itemsSize = items.size
        if (itemsSize <= 0) return "Total size: ${items.size} | ${selectedItem} "
        return "Total size: ${items.size} | $selectedItem | ${realIndex0()}"
    }

    fun realIndex0(): Int {
        val itemsSize = items.size
        if (itemsSize <= 0) {
            return 0
        }
        return virtualItemPosition % itemsSize
    }

    // Функция для получения элемента из списка по индексу
    fun getItem(index: Int): String {
        val realIndex = index % items.size
        return "${items[realIndex]} |$realIndex |$index"
    }
}