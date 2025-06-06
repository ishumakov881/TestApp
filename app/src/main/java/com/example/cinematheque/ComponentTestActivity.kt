package com.example.cinematheque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.tv.material3.*
import com.example.testapp.R


class ComponentTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComponentsScreen()
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TestComponentsScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp).background(Color.Yellow).verticalScroll(rememberScrollState()),
//        horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally),
//        verticalAlignment = Alignment.CenterVertically,

    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp).background(Color.Yellow),
            horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {


            var materialText by remember { mutableStateOf("") }
            var tvText by remember { mutableStateOf("") }
            var materialChecked by remember { mutableStateOf(false) }
            var tvChecked by remember { mutableStateOf(false) }
            var materialRadio by remember { mutableStateOf(false) }
            var tvRadio by remember { mutableStateOf(false) }
            var materialSlider by remember { mutableStateOf(0.5f) }
            var tvSlider by remember { mutableStateOf(0.5f) }

            // Левая колонка — обычные Material3
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//            }

            // Правая колонка — TV Material3
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                AssistChip(
                    onClick = { /* Открыть справку */ },
                    content = { Text("Справка") }
                )

                // Badge (если есть в TV Material)
                // Badge { Text("HD") }

                Button(onClick = { /* Перейти к просмотру */ }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Смотреть")
                }

                Card(
                    onClick = { /* Открыть детали фильма */ },
                    modifier = Modifier.size(200.dp, 300.dp)
                ) {
                    Image(painterResource(R.drawable.test), contentDescription = null)
                    Text("Интерстеллар")
                }

                Carousel(itemCount = 3, modifier = Modifier.height(200.dp)) { index ->
                    Image(painterResource(R.drawable.test), contentDescription = "Баннер $index")
                }

                var checked by remember { mutableStateOf(false) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checked, onCheckedChange = { checked = it })
                    Text("Добавить в избранное")
                }

                ClassicCard(
                    onClick = { /* Открыть фильм */ },
                    title = { Text("classic card -> Матрица") },
                    image = { Image(painterResource(R.drawable.test), contentDescription = null) }
                )

                CompactCard(
                    onClick = { /* Открыть сериал */ },
                    title = { Text("compact card-> Друзья") },
                    image = { Image(painterResource(R.drawable.test), contentDescription = null) }
                )

            /*@@@@@*/    DenseListItem(
                    headlineContent = { Text("Профиль: Алексей") },
                    supportingContent = { Text("Премиум-подписка") },
                    leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                    onClick = {},
                    selected = false,
                )

                var filterSelected by remember { mutableStateOf(false) }
                FilterChip(
                    selected = filterSelected,
                    onClick = { filterSelected = !filterSelected },
                    content = { Text("Фильмы") }
                )

                Icon(Icons.Default.FavoriteBorder, contentDescription = "Фильм", modifier = Modifier.size(48.dp))

                IconButton(onClick = { /* Поставить лайк */ }) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Лайк")
                }

                InputChip(
                    selected = false,
                    onClick = { /* Ввести жанр */ },
                    content = { Text("Жанр: Фантастика") }
                )

                /*@@@@@*/  ListItem(
                    headlineContent = { Text("История просмотров") },
                    supportingContent = { Text("Последний: Интерстеллар") },
                    leadingContent = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
                    onClick = {}, selected = false
                )

                MaterialTheme {
                    Text("Оформление приложения")
                }

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Column {
                            Text("Профиль")
                            Text("Настройки")
                        }
                    }
                ) {
                    Text("Главная страница")
                }

                // NavigationDrawer (если есть отдельный компонент)
                // NavigationDrawer(drawerContent = { ... }) { ... }

                OutlinedButton(onClick = { /* Добавить в список */ }) {
                    Text("В список")
                }

                OutlinedIconButton(onClick = { /* Добавить в избранное */ }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "В избранное")
                }

                ProvideTextStyle(value = TextStyle(fontSize = 20.sp, color = Color.Red)) {
                    Text("Премиум фильм")
                }

                var radioSelected by remember { mutableStateOf(true) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = radioSelected, onClick = { radioSelected = !radioSelected })
                    Text("Только HD")
                }

                // StandardCardContainer (пример, если есть реализация)
                // StandardCardContainer { Text("Карточки фильмов") }

                SuggestionChip(
                    onClick = { /* Предложить жанр */ },
                    content = { Text("Попробуйте: Комедия") }
                )

                Surface(
                    //colors = Color.DarkGray,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Фон для карточки")
                }

                var switchChecked by remember { mutableStateOf(false) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = switchChecked, onCheckedChange = { switchChecked = it })
                    Text("Включить уведомления")
                }

                var selectedTab by remember { mutableStateOf(0) }
                val tabs = listOf("Фильмы", "Сериалы", "Избранное")
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            content = { Text(title) },
                            //onFocus = TODO(),
                            modifier = Modifier,
                            enabled = true,
                            onFocus = {},
                        )
                    }
                }

                Text("Добро пожаловать в Кинотеатр!", style = MaterialTheme.typography.headlineMedium)

                var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }
                TriStateCheckbox(state = triState, onClick = {
                    triState = when (triState) {
                        ToggleableState.On -> ToggleableState.Off
                        ToggleableState.Off -> ToggleableState.Indeterminate
                        ToggleableState.Indeterminate -> ToggleableState.On
                    }
                })

                // WideButton (пример, если есть реализация)
                // WideButton(onClick = { /* Купить подписку */ }) { Text("Купить подписку") }

                // WideCardContainer (пример, если есть реализация)
                // WideCardContainer { Text("Эксклюзивные фильмы") }

                // WideClassicCard (пример, если есть реализация)
                // WideClassicCard(
                //     onClick = { /* Открыть подборку */ },
                //     title = { Text("Лучшие сериалы") },
                //     image = { Image(painterResource(R.drawable.test), contentDescription = null) }
                // )
            }
        }
    }

}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCardsShowcase() {
    val poster = painterResource(R.drawable.test) // Замени на свой ресурс

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text("ClassicCard", style = MaterialTheme.typography.titleLarge)
        ClassicCard(
            onClick = { /* Открыть фильм */ },
            title = { Text("Интерстеллар") },
            image = { Image(poster, contentDescription = null) }
        )

        Text("CompactCard", style = MaterialTheme.typography.titleLarge)
        CompactCard(
            onClick = { /* Открыть сериал */ },
            title = { Text("Друзья") },
            image = { Image(poster, contentDescription = null) }
        )

        Text("Card", style = MaterialTheme.typography.titleLarge)
        Card(
            onClick = { /* Открыть детали */ },
            modifier = Modifier.size(200.dp, 300.dp)
        ) {
            Image(poster, contentDescription = null)
            Text("Матрица")
        }

        Text("WideClassicCard", style = MaterialTheme.typography.titleLarge)
        // WideClassicCard — если есть реализация
        // WideClassicCard(
        //     onClick = { /* Открыть подборку */ },
        //     title = { Text("Лучшие сериалы") },
        //     image = { Image(poster, contentDescription = null) }
        // )

        Text("StandardCardContainer", style = MaterialTheme.typography.titleLarge)
        // StandardCardContainer — если есть реализация
        // StandardCardContainer {
        //     Text("Карточки фильмов")
        // }

        Text("WideCardContainer", style = MaterialTheme.typography.titleLarge)
        // WideCardContainer — если есть реализация
        // WideCardContainer {
        //     Text("Эксклюзивные фильмы")
        // }
    }
}