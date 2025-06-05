package com.example.cinematheque

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.berggren.dpadFocusable0
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppWithDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Фокус для бургера
    val burgerFocusRequester = remember { FocusRequester() }
    var isFirstItemFocused by remember { mutableStateOf(false) }

    // Гарантированная установка фокуса после первого composition
//    val view = LocalView.current
//    LaunchedEffect(view) {
//        // Ждём полной инициализации вьюшки
//        view.post {
//            burgerFocusRequester.requestFocus()
//        }
//    }
    LaunchedEffect(Unit) {
        if (!isFirstItemFocused) {
            burgerFocusRequester.requestFocus()
            isFirstItemFocused = true
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Меню", modifier = Modifier.padding(16.dp))
                repeat(5) {
                    var isFocused by remember { mutableStateOf(false) }
                    Text(
                        text = "Пункт $it",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .onFocusChanged { isFocused = it.isFocused }
                            .focusable()
                            .background(if (isFocused) Color.LightGray else Color.Transparent)
                            .then(
                                if (isFocused) {
                                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(4.dp))
                                } else {
                                    Modifier
                                }
                            )
                            .padding(8.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Медиа Центр") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                            null
                            //coroutineScope.launch { drawerState.open() }
                            },
//                            modifier = Modifier
//                                .focusRequester(burgerFocusRequester)
//                                .focusable() // ← обязательно перед .focusRequester
//                                .onFocusChanged {
//                                    val isFocused = it.isFocused
//                                    println("@@@: $isFocused")
//                                    if (it.isFocused) println("Бургер в фокусе")
//                                }
//                                .dpadFocusable(
//                                    onClick = {},
////                                    borderWidth = TODO(),
////                                    unfocusedBorderColor = TODO(),
////                                    focusedBorderColor = TODO(),
////                                    indication = TODO(),
////                                    scrollPadding = TODO(),
////                                    isDefault = TODO()
//                                )
//                                .focusProperties {
//                                    exit = { FocusRequester.Cancel } // Не выйти в null
//                                }

                            modifier = Modifier.focusRequester(burgerFocusRequester)
                                .dpadFocusable(
                                    onClick = {coroutineScope.launch { drawerState.open() }},
//                                    borderWidth = TODO(),
//                                    unfocusedBorderColor = TODO(),
//                                    focusedBorderColor = TODO(),
//                                    indication = TODO(),
//                                    scrollPadding = TODO(),
                                    isDefault = true
                                )

                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    }
                )
            }
        ) { padding ->
            MovieGrid(
                rows = 4,
                cols = 4,
                modifier = Modifier.padding(padding),
                onClick = { row, col ->
                    println("Клик по $row x $col")
                    burgerFocusRequester.requestFocus()
                })
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver", "UnnecessaryComposedModifier")
private fun Modifier.dpadFocusable(
    onClick: () -> Unit,
    borderWidth: Dp = 4.dp,
    unfocusedBorderColor: Color = Color(0x00f39c12),
    focusedBorderColor: Color = Color(0xfff39c12),
    indication: Indication? = null,
    scrollPadding: Rect = Rect.Zero,
    isDefault: Boolean = false
) = composed {
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val boxInteractionSource = remember { MutableInteractionSource() }
    val isItemFocused by boxInteractionSource.collectIsFocusedAsState()
    val animatedBorderColor by animateColorAsState(
        targetValue =
            if (isItemFocused) focusedBorderColor
            else unfocusedBorderColor
    )
    var previousFocus: FocusInteraction.Focus? by remember {
        mutableStateOf(null)
    }
    var previousPress: PressInteraction.Press? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    var boxSize by remember {
        mutableStateOf(IntSize(0, 0))
    }
    val inputMode = LocalInputModeManager.current

    LaunchedEffect(inputMode.inputMode) {
        when (inputMode.inputMode) {
            InputMode.Keyboard -> {
                if (isDefault) {
                    focusRequester.requestFocus()
                }
            }

            InputMode.Touch -> {}
        }
    }
    LaunchedEffect(isItemFocused) {
        previousPress?.let {
            if (!isItemFocused) {
                boxInteractionSource.emit(
                    PressInteraction.Release(
                        press = it
                    )
                )
            }
        }
    }

    if (inputMode.inputMode == InputMode.Touch)
        this.clickable(
            interactionSource = boxInteractionSource,
            indication = indication ?: ripple()
        ) {
            onClick()
        }
    else
        this
            .bringIntoViewRequester(bringIntoViewRequester)
            .onSizeChanged {
                boxSize = it
            }
            .indication(
                interactionSource = boxInteractionSource,
                indication = indication ?: ripple()
            )
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    val newFocusInteraction = FocusInteraction.Focus()
                    scope.launch {
                        boxInteractionSource.emit(newFocusInteraction)
                    }
                    scope.launch {
                        val visibilityBounds = Rect(
                            left = -1f * scrollPadding.left,
                            top = -1f * scrollPadding.top,
                            right = boxSize.width + scrollPadding.right,
                            bottom = boxSize.height + scrollPadding.bottom
                        )
                        bringIntoViewRequester.bringIntoView(visibilityBounds)
                    }
                    previousFocus = newFocusInteraction
                } else {
                    previousFocus?.let {
                        scope.launch {
                            boxInteractionSource.emit(FocusInteraction.Unfocus(it))
                        }
                    }
                }
            }
            .onKeyEvent {
                if (!listOf(Key.DirectionCenter, Key.Enter).contains(it.key)) {
                    return@onKeyEvent false
                }
                when (it.type) {
                    KeyEventType.KeyDown -> {
                        val press =
                            PressInteraction.Press(
                                pressPosition = Offset(
                                    x = boxSize.width / 2f,
                                    y = boxSize.height / 2f
                                )
                            )
                        scope.launch {
                            boxInteractionSource.emit(press)
                        }
                        previousPress = press
                        true
                    }

                    KeyEventType.KeyUp -> {
                        previousPress?.let { previousPress ->
                            onClick()
                            scope.launch {
                                boxInteractionSource.emit(
                                    PressInteraction.Release(
                                        press = previousPress
                                    )
                                )
                            }
                        }
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            .focusRequester(focusRequester)
            .focusTarget()
            .border(
                width = borderWidth,
                color = animatedBorderColor
            )
}
