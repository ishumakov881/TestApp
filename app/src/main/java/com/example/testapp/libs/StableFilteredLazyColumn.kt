package com.example.testapp.libs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun StableFilteredLazyColumn() {
    var filteredList by remember { mutableStateOf<List<Int>>(emptyList()) }
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val FIND_ME = 3

    LaunchedEffect(Unit) {
        repeat(100) { i ->
            delay(5000)

            val newList = if (i % 2 == 0) {
                listOf(
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    4,
                    (55..100).random(),
                    (55..100).random(),
                    FIND_ME,
                    (55..100).random(),
                    (55..100).random()
                )
            } else {
                listOf(
                    (55..100).random(),
                    5,
                    4,
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    (55..100).random(),
                    FIND_ME
                )
            }

            // Обновляем список и только затем скроллим
            filteredList = newList

            // Ждём следующего кадра для гарантии обновления
            delay(16.milliseconds) // ~1 кадр при 60fps

            val newIndex = newList.indexOf(FIND_ME)
            if (newIndex != -1) {
                lazyListState.scrollToItem(newIndex)
            }
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = filteredList,
            key = { index, _ -> "key_$index" }
        ) { _, item ->
            Text(
                text = item.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(if (item == FIND_ME) Color.Yellow else Color.Transparent)
            )
        }
    }
}