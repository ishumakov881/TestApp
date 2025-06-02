package com.example.cinematheque

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.onFocusChanged

// +1 for ProfileTab
val TopBarFocusRequesters = List(size = 1) { FocusRequester() }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Для Android TV - полноэкранный режим
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val initialFocusRequester : List<FocusRequester> = remember { TopBarFocusRequesters }

                    //AppWithDrawer()
                    MovieGridScreenSimple(
                        initialFocusRequester = initialFocusRequester[0]
                    )
                }

            }
        }
    }
}

//@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun AppWithDrawer() {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val coroutineScope = rememberCoroutineScope()
//
//    // Фокус для бургера
//    val burgerFocusRequester = remember { FocusRequester() }
//    var isFirstItemFocused by remember { mutableStateOf(false) }
//
//    // Гарантированная установка фокуса после первого composition
////    val view = LocalView.current
////    LaunchedEffect(view) {
////        // Ждём полной инициализации вьюшки
////        view.post {
////            burgerFocusRequester.requestFocus()
////        }
////    }
//    LaunchedEffect(Unit) {
//        if (!isFirstItemFocused) {
//            burgerFocusRequester.requestFocus()
//            isFirstItemFocused = true
//        }
//    }
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                Text("Меню", modifier = Modifier.padding(16.dp))
//                repeat(5) {
//                    var isFocused by remember { mutableStateOf(false) }
//                    Text(
//                        text = "Пункт $it",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                            .onFocusChanged { isFocused = it.isFocused }
//                            .focusable()
//                            .background(if (isFocused) Color.LightGray else Color.Transparent)
//                            .then(
//                                if (isFocused) {
//                                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(4.dp))
//                                }else {
//                                    Modifier
//                                }
//                            )
//                            .padding(8.dp)
//                    )
//                }
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Медиа Центр") },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                coroutineScope.launch { drawerState.open() }
//                            },
//                            modifier = Modifier
//                                .focusRequester(burgerFocusRequester)
//                                .focusable() // ← обязательно перед .focusRequester
//                                .onFocusChanged {
//                                    val isFocused = it.isFocused
//                                    println("@@@: $isFocused")
//                                    if (it.isFocused) println("Бургер в фокусе")
//                                }
//                                .focusProperties {
//                                    exit = { FocusRequester.Cancel } // Не выйти в null
//                                }
//                        ) {
//                            Icon(Icons.Default.Menu, contentDescription = "Меню")
//                        }
//                    }
//                )
//            }
//        ) { padding ->
//            MovieGrid(rows = 4, cols = 4, modifier = Modifier.padding(padding), onClick = {
//                row, col ->
//                println("Клик по $row x $col")
//                burgerFocusRequester.requestFocus()
//            })
//        }
//    }
//}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovieGrid(rows: Int, cols: Int, modifier: Modifier = Modifier, onClick: (Int, Int) -> Unit = { _, _ -> }){
    val initialFocusRequester = remember { FocusRequester() }
    var isFirstItemFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        repeat(rows) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(cols) { col ->
                    var isFocused by remember { mutableStateOf(false) }

                    val focusModifier = Modifier
                        .size(160.dp)
                        .padding(4.dp)
                        .onFocusChanged {
                            isFocused = it.isFocused
                            println("Focus changed: $isFocused")
                        }
                        .focusable()
                        .focusProperties {
                            if (col == 0) left = FocusRequester.Cancel
                            if (col == cols - 1) right = FocusRequester.Cancel
                            if (row == 0) up = FocusRequester.Cancel
                            if (row == rows - 1) down = FocusRequester.Cancel
                        }
                        .clickable {
                            onClick(row, col)
                        }
                        .then(
                            if (row == 0 && col == 0)
                                Modifier.focusRequester(initialFocusRequester)
                            else Modifier
                        )

                    Card(
                        modifier = focusModifier,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isFocused) 8.dp else 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isFocused) Color(0xFFE3F2FD) else Color.White
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
                                        .border(2.dp, Color.Red, RoundedCornerShape(8.dp))
                                )
                            }
                            Text("$row x $col", modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!isFirstItemFocused) {
            initialFocusRequester.requestFocus()
            isFirstItemFocused = true
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovieGridScreen2() {
    val rows = 4
    val cols = 4

    val initialFocusRequester = remember { FocusRequester() }
    var isFirstItemFocused by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()) {

        // ===== Боковое меню =====
        Column(
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
                .background(Color(0xFF263238))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf("Главная", "Фильмы", "Сериалы", "Избранное").forEach { title ->
                var isFocused by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .onFocusChanged { isFocused = it.isFocused }
                        .focusable()
                        .background(if (isFocused) Color.DarkGray else Color.Transparent)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(text = title, color = Color.White)
                }
            }
        }

        // ===== Основной контент =====
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // ===== Тулбар =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.End
            ) {
                var isFocused by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .onFocusChanged { isFocused = it.isFocused }
                        .focusable()
                        .clickable { println("Настройки нажаты") }
                        .background(if (isFocused) Color.Gray else Color.LightGray)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Настройки", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ===== Сетка фильмов =====
            repeat(rows) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(cols) { col ->
                        var isFocused by remember { mutableStateOf(false) }

                        val focusModifier = Modifier
                            .size(160.dp)
                            .padding(4.dp)
                            .onFocusChanged { isFocused = it.isFocused }
                            .focusable()
                            .focusProperties {
                                if (col == 0) left = FocusRequester.Cancel
                                if (col == cols - 1) right = FocusRequester.Cancel
                                if (row == 0) up = FocusRequester.Cancel
                                if (row == rows - 1) down = FocusRequester.Cancel
                            }
                            .clickable {
                                println("Клик: $row x $col")
                            }
                            .then(
                                if (row == 0 && col == 0)
                                    Modifier.focusRequester(initialFocusRequester)
                                else Modifier
                            )

                        Card(
                            modifier = focusModifier,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = if (isFocused) 8.dp else 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isFocused) Color(0xFFE3F2FD) else Color.White
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
                                                2.dp,
                                                Color.Red,
                                                RoundedCornerShape(8.dp)
                                            )
                                    )
                                }
                                Text("$row x $col", modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            }
        }
    }

    // Установка начального фокуса
    LaunchedEffect(Unit) {
        if (!isFirstItemFocused) {
//            initialFocusRequester.requestFocus()
//            isFirstItemFocused = true
        }
    }
}


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Настройка для Android TV
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
//
////        // Добавляем обработку клавиш D-pad
////        window.decorView.setOnKeyListener { _, keyCode, event ->
////            when (keyCode) {
////                KeyEvent.KEYCODE_DPAD_UP -> {
////                    // Перемещаем фокус вверх
////                    true
////                }
////                KeyEvent.KEYCODE_DPAD_DOWN -> {
////                    // Перемещаем фокус вниз
////                    true
////                }
////                KeyEvent.KEYCODE_DPAD_LEFT -> {
////                    // Перемещаем фокус влево
////                    true
////                }
////                KeyEvent.KEYCODE_DPAD_RIGHT -> {
////                    // Перемещаем фокус вправо
////                    true
////                }
////                else -> false
////            }
////        }
//
//        setContent {
//
//            val focusRequesters = remember {
//                List(4) { // 4 строки
//                    List(4) { FocusRequester() } // 4 столбца
//                }
//            }
//
//
//            TestAppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    //MovieGridScreen()
//                    MovieGridScreen1()
//                }
//            }
//        }
//    }
//
//
//}
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun MovieGridScreen1() {
//    val rows = 4
//    val cols = 4
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        repeat(rows) { row ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                repeat(cols) { col ->
//                    val interactionSource = remember { MutableInteractionSource() }
//                    val isFocused = interactionSource.collectIsFocusedAsState().value
//
//                    Card(
//                        modifier = Modifier
//                            .size(160.dp)
//                            .padding(4.dp)
//                            .focusable(interactionSource = interactionSource)
//                            .onFocusChanged {
//                                if (it.isFocused) {
//                                    println("Focused: [$row][$col]")
//                                }
//                            }
//                            .focusProperties {
//                                // Отключаем движение, если элемент крайний
//                                if (col == 0) left = FocusRequester.Cancel
//                                if (col == cols - 1) right = FocusRequester.Cancel
//                                if (row == 0) up = FocusRequester.Cancel
//                                if (row == rows - 1) down = FocusRequester.Cancel
//                            }
//                            .clickable {
//                                println("Clicked: [$row][$col]")
//                            },
//                        elevation = CardDefaults.cardElevation(
//                            defaultElevation = if (isFocused) 8.dp else 4.dp
//                        ),
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (isFocused)
//                                Color(0xFFE3F2FD)
//                            else
//                                Color.White
//                        )
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(8.dp)
//                        ) {
//                            if (isFocused) {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .border(
//                                            width = 2.dp,
//                                            color = Color.Red,
//                                            shape = RoundedCornerShape(8.dp)
//                                        )
//                                )
//                            }
//                            Text("$row x $col")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun MovieGridScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        for (row in 0..3) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                for (col in 0..3) {
//                    val interactionSource = remember { MutableInteractionSource() }
//                    val isFocused = interactionSource.collectIsFocusedAsState().value
//
//                    Card(
//                        modifier = Modifier
//                            .size(160.dp)
//                            .padding(4.dp)
//                            .focusable(interactionSource = interactionSource)
//                            .onFocusChanged {
//                                if (it.isFocused) {
//
//                                }
//                            }
//
//                            .focusProperties {
//                                // Настраиваем навигацию между карточками
//
//                                left = if (row == 0 && col == 0) {
//                                    FocusRequester.Cancel
//                                } else {
//                                    FocusRequester.Default
//                                }
//
//                                next = if (row == 0 && col == 0) {
//                                    FocusRequester.Cancel
//                                } else {
//                                    FocusRequester.Default
//                                }
//                                previous = if (row == 0 && col == 0) {
//                                    FocusRequester.Cancel
//                                } else {
//                                    FocusRequester.Default
//                                }
//                                up = if (row == 0 && col == 0) {
//                                    FocusRequester.Cancel
//                                } else {
//                                    FocusRequester.Default
//                                }
//                                down = if (row == 0 && col == 0) {
//                                    FocusRequester.Cancel
//                                } else {
//                                    FocusRequester.Default
//                                }
//                            }
//                            .clickable {
//
//                            },
//                        elevation = CardDefaults.cardElevation(
//                            defaultElevation = if (isFocused) 8.dp else 4.dp
//                        ),
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (isFocused)
//                                Color(0xFFE3F2FD)
//                            else
//                                Color.White
//                        )
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(8.dp)
//                        ) {
//                            if (isFocused) {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .border(
//                                            width = 2.dp,
//                                            color = Color.Red,
//                                            shape = RoundedCornerShape(8.dp)
//                                        )
//                                )
//                            }
//
//
//                            Text("$row x $col")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}