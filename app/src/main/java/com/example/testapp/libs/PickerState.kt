package com.lds.cinema.ui.screen.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberPickerState(): MutableState<PickerState> = remember { mutableStateOf(PickerState()) }

data class PickerState(
    val items: List<String> = emptyList(),
    val _realItemPosition: Int = 0,
) {
    var virtualItemPosition: Int = 0

    var realItemPosition: Int = _realItemPosition
        set(value) {
            field = if (items.isEmpty()) 0 else value % items.size
            selectedItem = items.getOrNull(field) ?: ""
        }

    var selectedItem: String = ""
        get() = items.getOrNull(realItemPosition) ?: ""

    fun prettyPrint(): String {
        return if (items.isEmpty()) {
            "Empty list"
        } else {
            "Total size: ${items.size} | Selected: $selectedItem | Real: $realItemPosition"
        }
    }

    fun realIndex0(): Int {
        return if (items.isEmpty()) 0 else virtualItemPosition % items.size
    }

    fun getItem(index: Int): String {
        return if (items.isEmpty()) "" else items[index % items.size]
    }

    fun getLabel(index: Int): String {
        if (items.isEmpty()) return ""
        val realIndex = index % items.size
        return if (items.isEmpty()) "" else items[index % items.size]
        //return "${items[realIndex]} | $realIndex | $index"
    }
}