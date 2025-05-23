package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testapp.ui.theme.TestAppTheme
import com.lds.cinema.ui.screen.filter.YearRangePickerDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    println(innerPadding)

                    val availableYears = (2000..2024).toList().map {
                        it.toString()
                    }

                    // Находим индексы выбранных годов в списке доступных годов
                    val fromYearIndex = 0 // Для специального значения "с"

                    val toYearIndex = availableYears.size - 1 // Для специального значения "по"

                    YearRangePickerDialog(
                        initialFromYearIndex = fromYearIndex,
                        initialToYearIndex = toYearIndex,
                        availableYears = availableYears,
                        onDismiss = { },
                        onReset = {
                            //@@
                        },
                        onConfirm = { fromYear, toYear ->
                            //@@@
                        }
                    )
                }
            }
        }
    }
}