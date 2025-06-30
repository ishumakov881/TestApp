package com.example.cinematheque.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ColumnTest(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .height(IntrinsicSize.Min) // 👈 LazyColumn так не умеет
    ) {
        Text("Item 1", modifier = Modifier.weight(1f).fillMaxHeight())
        Divider(color = Color.Gray, thickness = 1.dp)
        Text("Item 2", modifier = Modifier.weight(1f).fillMaxHeight())
    }
}