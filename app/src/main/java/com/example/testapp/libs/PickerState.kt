package com.lds.cinema.ui.screen.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberPickerState(): MutableState<PickerState> = remember { mutableStateOf(PickerState()) }

data class PickerState(
    val items: List<String> = emptyList(),
    val realItemPosition: Int = 0
) {
    val selectedItem: String
        get() = items.getOrNull(realItemPosition) ?: ""

    val prettyPrint: String
        get() = if (items.isEmpty()) {
            "Empty list"
        } else {
            "Total size: ${items.size} | Selected: $selectedItem | Real: $realItemPosition"
        }

    fun getLabel(index: Int): String {
        return if (items.isEmpty()) ""  else items[index]
    }

    fun withNewPosition(position: Int): PickerState {
        val corrected = if (items.isEmpty()) 0 else position % items.size
        return copy(realItemPosition = corrected)
    }
}