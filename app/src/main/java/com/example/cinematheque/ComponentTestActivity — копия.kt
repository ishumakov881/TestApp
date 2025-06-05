//package com.example.cinematheque
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.focusable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material3.*
//import androidx.compose.material3.Button as MaterialButton
//import androidx.compose.material3.IconButton as MaterialIconButton
//import androidx.compose.material3.Text as MaterialText
//import androidx.compose.material3.Card as MaterialCard
//
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.tv.material3.*
//import androidx.tv.material3.Button as TvButton
//import androidx.tv.material3.IconButton as TvIconButton
//import androidx.tv.material3.Text as TvText
//import androidx.tv.material3.Card as TvCard
//
//
//import androidx.tv.material3.Checkbox as TvCheckbox
//import androidx.tv.material3.RadioButton as TvRadioButton
////import androidx.tv.material3.Slider as TvSlider
//
//
//class ComponentTestActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            TestComponentsScreen()
//        }
//    }
//}
//
//@Composable
//fun TestComponentsScreen() {
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(0.dp).background(Color.Yellow).verticalScroll(rememberScrollState()),
////        horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally),
////        verticalAlignment = Alignment.CenterVertically,
//
//    ){
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(0.dp).background(Color.Yellow),
//            horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//
//
//            var materialText by remember { mutableStateOf("") }
//            var tvText by remember { mutableStateOf("") }
//            var materialChecked by remember { mutableStateOf(false) }
//            var tvChecked by remember { mutableStateOf(false) }
//            var materialRadio by remember { mutableStateOf(false) }
//            var tvRadio by remember { mutableStateOf(false) }
//            var materialSlider by remember { mutableStateOf(0.5f) }
//            var tvSlider by remember { mutableStateOf(0.5f) }
//
//            // Левая колонка — обычные Material3
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                MaterialText("Material3", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
//
//                MaterialText("Click me", style = androidx.compose.material3.MaterialTheme.typography.titleLarge
//                    , modifier = Modifier.focusable().clickable {
//                        println("Material3 clicked")
//                    })
//
//                MaterialButton(onClick = {}) {
//                    MaterialText("Button")
//                }
//                MaterialIconButton(onClick = {}) {
//                    androidx.tv.material3.Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
//                }
//                MaterialCard(
//                    modifier = Modifier.size(120.dp, 80.dp),
//                    onClick = {}
//                ) {
//                    Box(
//                        Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        MaterialText("Card")
//                    }
//                }
//                MaterialButton(onClick = {}) {
//                    androidx.tv.material3.Icon(Icons.Filled.Star, contentDescription = "Star")
//                    Spacer(Modifier.width(8.dp))
//                    MaterialText("Button+Icon")
//                }
//
//
//                Checkbox(
//                    checked = materialChecked,
//                    onCheckedChange = { materialChecked = it }
//                )
//                RadioButton(
//                    selected = materialRadio,
//                    onClick = { materialRadio = !materialRadio }
//                )
//                Slider(
//                    value = materialSlider,
//                    onValueChange = { materialSlider = it }
//                )
//            }
//
//            // Правая колонка — TV Material3
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TvText(text="TV Material3", style = androidx.tv.material3.MaterialTheme.typography.titleLarge)
//                TvText(text="Click me", style = androidx.tv.material3.MaterialTheme.typography.titleLarge,
//                    modifier =  Modifier.clickable {
//                        println("TV Material3 clicked")
//                    })
//
//
//
//
//                TvButton(onClick = {}) {
//                    TvText("Button")
//                }
//                TvIconButton(onClick = {}) {
//                    androidx.tv.material3.Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
//                }
//                TvCard(
//                    modifier = Modifier.size(120.dp, 80.dp),
//                    onClick = {}
//                ) {
//                    Box(
//                        Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        TvText("Card")
//                    }
//                }
//                TvButton(onClick = {}) {
//                    androidx.tv.material3.Icon(Icons.Filled.Star, contentDescription = "Star")
//                    Spacer(Modifier.width(8.dp))
//                    TvText("Button+Icon")
//                }
//
//
//                TvCheckbox(
//                    checked = tvChecked,
//                    onCheckedChange = { tvChecked = it }
//                )
//                TvRadioButton(
//                    selected = tvRadio,
//                    onClick = { tvRadio = !tvRadio }
//                )
//
//
//
//                AssistChip(
//                    onClick = { /* Открыть справку */ },
//                    label = { TvText("Справка") }
//                )
//
//
//            }
//        }
//    }
//
//}