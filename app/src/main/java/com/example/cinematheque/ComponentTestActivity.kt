package com.example.cinematheque

import android.annotation.SuppressLint
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.cinematheque.ui.components.ColumnTest
import com.example.testapp.R
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ComponentTestActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //TestComponentsScreen()
            ColumnTest()

            var coroutineScope = rememberCoroutineScope()
            coroutineScope.launch {
                processUser()
            }

            coroutineScope.launch {
                val x = async {
                    "Test 1"
                }
                val v = async {
                    "test 2"
                }
                combineImages(x.await(), v.await())

            }

            println()

            //value is String
            val m = 111
//            when(m){
//                is Int -> println("1")
//                is String -> println("22")
//            }
//
//            while(condition){
//                if(shouldExit) break
//            }
//
//            do{
//                if(shouldSkip) continue
//            }while (condition){
//
//            }
//
//
//            outer@ while (outerCondition) {
//                while (innerCondition) {
//                    if (shouldExitInner) break
//                    if (shouldSkipInner) continue
//                    if (shouldExit) break@outer
//                    if (shouldSkip) continue@outer
//// ...
//                }
//// ...
//                for (color in collection) {
//                    print("$color ")
//                }
//            }


            val binaryReps = mutableMapOf<Char, String>()
            for (char in 'A'..'F') {
                val binary = char.code.toString(radix = 2)
                binaryReps[char] = binary
            }
            for ((letter, binary) in binaryReps) {
                println("$letter = $binary")
            }
// A = 1000001 D = 1000100
// B = 1000010 E = 1000101
// C = 1000011 F = 1000110

//                setOf(), listOf, mapOf


            val strings: List<String> = listOf("first", "second", "fourteenth")
            strings.last()
            // fourteenth
            val numbers: Collection<Int> = setOf(1, 14, 2)
            numbers.sum()


            val name = "AAA"
            val c = name.substringBefore("@").trimIndent()
            A.B()
            A.C()

            //hashSetOf()
            //other as? Person
//            x.let { … } it Результат выполнения лямбды
//            x.also { … } it x
//            x.apply { … } this x
//                x.run { … } this Результат выполнения лямбды
//                with(x) { … } this Результат выполнения лямбды

            ".".trimIndent()
            val validNumbers = numbers.filterNotNull()

//            a * b times
//                    a / b div
//                    a % b mod
//                    a + b plus
//                    a – b minus
            println(A(1,1)+A(2,2))

        }
    }
}


class A(val x: Int, val y: Int) {


    inner class C {
        fun test(): A {
            return this@A
        }
    }

    class B {
        var counter: Int = 0
            private set

        fun addWords(words: String) {
            counter += words.length
        }
    }


    operator fun plus(b: A): A {
        return A(this.x + b.x, this.y + b.y)
    }

    operator fun plusAssign(){

    }
    operator fun minusAssign(){

    }
    +a unaryPlus
    a unaryMinus
    !a not
    ++a, a++ inc
    a, a dec
}
fun main() {
    var a = " aa "
    var x = a as String

    val c = listOf("1@", "2@","3@")

    val m = c.reduce{
            acc, item->
        acc + item
    }
    val v = c.fold("###"){
            acc, item->
        acc + item
    }

    println(m)
    println(v)

    var next = 1
    x@ while(next<10){
        next += 1
        if(next == 5){
            break@x
        }
        println("$next")
    }

    val text = "1"
    val result = mutableListOf<Int?>()
    for (line in text.lineSequence()) {
        val numberOrNull = line.toIntOrNull()
        result.add(numberOrNull)
    }
    println(result)

    println("Hello, world!!! $x")
}
private fun combineImages(await: String, await1: String) {
    println(await1 + await)
}

suspend fun processUser() {
    val user = autentificate()
    val data = loadUserData(user)

}

suspend fun loadUserData(user: String): Any {
    for (i in 0 until 100) {
        println("##$i")
    }
    return "xxx"
}

suspend fun autentificate(): String {
    for (i in 0 until 4) {
        println("@@$i")
    }
    return "@@"
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TestComponentsScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(Color.Yellow)
            .verticalScroll(rememberScrollState()),
//        horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally),
//        verticalAlignment = Alignment.CenterVertically,

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .background(Color.Yellow),
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

                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Фильм",
                    modifier = Modifier.size(48.dp)
                )

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
                    RadioButton(
                        selected = radioSelected,
                        onClick = { radioSelected = !radioSelected })
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

                Text(
                    "Добро пожаловать в Кинотеатр!",
                    style = MaterialTheme.typography.headlineMedium
                )

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