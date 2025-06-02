package com.example.cinematheque

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testapp.ui.theme.TestAppTheme
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import android.view.KeyEvent
import androidx.compose.foundation.clickable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Настройка для Android TV
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Добавляем обработку клавиш D-pad
        window.decorView.setOnKeyListener { _, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_UP -> {
                    // Перемещаем фокус вверх
                    true
                }
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    // Перемещаем фокус вниз
                    true
                }
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    // Перемещаем фокус влево
                    true
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    // Перемещаем фокус вправо
                    true
                }
                else -> false
            }
        }

        setContent {
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieGridScreen()
                }
            }
        }
    }

    @Composable
    fun MovieGridScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            for (row in 0..3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0..3) {
                        val interactionSource = remember { MutableInteractionSource() }
                        val isFocused = interactionSource.collectIsFocusedAsState().value

                        Card(
                            modifier = Modifier
                                .size(160.dp)
                                .padding(4.dp)
                                .focusable(interactionSource = interactionSource)
                                .focusProperties {
                                    // Настраиваем навигацию между карточками
//                                    next = FocusDirection.Right
//                                    previous = FocusDirection.Left
//                                    up = FocusDirection.Up
//                                    down = FocusDirection.Down
                                }.clickable {

                                },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = if (isFocused) 8.dp else 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isFocused)
                                    Color(0xFFE3F2FD)
                                else
                                    Color.White
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
                            }
                        }
                    }
                }
            }
        }
    }
}