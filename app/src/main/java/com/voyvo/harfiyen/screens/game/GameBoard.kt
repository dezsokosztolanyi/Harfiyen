package com.voyvo.dragdrop.screens.game

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.voyvo.dragdrop.*
import com.voyvo.dragdrop.navigation.Screen
import com.voyvo.dragdrop.screens.home.GameOverDialog
import com.voyvo.dragdrop.screens.puzzles.getPuzzleBackgroundColor
import com.voyvo.dragdrop.util.WindowSize
import com.voyvo.harfiyen.R
import com.voyvo.harfiyen.ui.theme.*

import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun GameBoard(
    vm: GameViewModel,
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: GameState,
    question: Question,
    enabled: Boolean,
    navHostController: NavHostController,
    letterClicked: (Int) -> Unit,
    celebrateAnimation: Boolean,
    windowSize : WindowSize
) {
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    var itemWidthTemp by remember {
        mutableStateOf(width.dp)
    }
    val titleHeight = (height * 0.18f).dp
    val titleWidth = (width * 0.5f).dp

    val itemWidth = (itemWidthTemp / vm.letters.size)
    val state = rememberReorderableLazyListState(onMove = vm::moveLetter)

    var pauseState by remember {
        mutableStateOf(false)
    }

    val soundState by vm.soundState.collectAsState()
    val nextLevelId by vm.nextLevelId.collectAsState()
    val gameOver by vm.gameOver.collectAsState()


    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray), contentAlignment = Alignment.Center
    ) {

        if (this@BoxWithConstraints.maxWidth != itemWidthTemp) {
            itemWidthTemp = this@BoxWithConstraints.maxWidth + 1.dp
        }

        AnimatedVisibility(visible = gameState == GameState.ANSWERING) {

            LazyRow(
                state = state.listState,
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = modifier.then(Modifier.reorderable(state)),
            ) {
                items(vm.letters, { item -> item.id }) { item ->
                    println("lazy items: ${vm.letters}")

                    ReorderableItem(state, item.id) { dragging ->

                        val scale = animateFloatAsState(if (dragging) 1.1f else 1.0f)
                        val elevation = if (dragging) 8.dp else 0.dp
                        var letterBackgroundColor by remember {
                            mutableStateOf(pickLetterBackgroundColor(item, question.type, vm))
                        }
                        var icon by remember {
                            mutableStateOf(getLetterIcon(item))
                        }
                        var currentLetter by remember {
                            mutableStateOf(item.letter)
                        }
                        var draggable by remember {
                            mutableStateOf(
                                Modifier
                                    .detectReorderAfterLongPress(state)
                            )
                        }

                        LaunchedEffect(key1 = true, block = {
                            currentLetter = item.letter
                            draggable = if (item.type == Type.DRAGGABLE) {
                                Modifier
                                    .detectReorderAfterLongPress(state)
                            } else {
                                Modifier
                            }
                        })

                        LaunchedEffect(key1 = question, block = {
                            letterBackgroundColor =
                                pickLetterBackgroundColor(item, question.type, vm)
                            icon = getLetterIcon(item)
                            currentLetter = item.letter
                            draggable = if (item.type == Type.DRAGGABLE) {
                                Modifier
                                    .detectReorderAfterLongPress(state)
                            } else {
                                Modifier
                            }
                        })

                        Column(
                            modifier = draggable
                                .fillMaxHeight()
                                .width(itemWidth)
                                .scale(scale.value)
                                .shadow(elevation)
                                .background(letterBackgroundColor)
                                .clickable {
                                    if (enabled) {
                                        if (item.type == Type.TRANSFORMABLE || item.type == Type.SELECTABLE) {
                                            letterClicked(item.id).also {
                                                if (item.type == Type.TRANSFORMABLE) {
                                                    currentLetter =
                                                        vm.getNewLetterAfterClick(item.id)
                                                }
                                                if (item.type == Type.SELECTABLE) {
                                                    icon = vm.getNewIconAfterClick(item.id)
                                                    letterBackgroundColor =
                                                        pickLetterBackgroundColor(
                                                            item,
                                                            question.type,
                                                            vm
                                                        )
                                                }
                                            }
                                        }
                                    }

                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Spacer(modifier = Modifier.size(40.dp))
                            TextWithShadow(
                                if (item.type == Type.DRAGGABLE) item.letter else currentLetter,
                                Modifier,
                                65
                            )
                            Image(
                                modifier = Modifier.size(itemWidth / 2),
                                painter = painterResource(id = icon),
                                contentDescription = item.letter
                            )
                        }

                    }
                }
            }
        }


        AnimatedVisibility(
            visible = gameState == GameState.ANSWERING,
            enter = slideInVertically(initialOffsetY = { it * -2 }, animationSpec = tween(600)),
            exit = shrinkOut(shrinkTowards = Alignment.CenterEnd)
        ) {
            QuestionBox(titleHeight = titleHeight, titleWidth = titleWidth, question = question,celebrateAnimation)
        }


        Pause(titleHeight = titleHeight, onPauseClicked = {
            pauseState = true
        })

        AnimatedVisibility(
            visible = pauseState,
            enter = slideInHorizontally(initialOffsetX = { it * -2 }, animationSpec = tween(600)),
            exit = slideOutHorizontally(targetOffsetX = { it * -2 }, animationSpec = tween(600))
        ) {
            Menu(onDialogClosed = {
                pauseState = false
            }, onSkipClicked = {
                pauseState = false
                vm.skipQuestion()
            }, onPuzzlesClicked = {
                pauseState = false
                navHostController.navigate(Screen.Puzzles.route)
            }, onSoundStateClicked = {
                vm.changeSoundState()
            },
                soundState = soundState
            )
        }

        AnimatedVisibility(
            visible = gameState == GameState.ANIMATION,
            enter = slideInHorizontally(
                initialOffsetX = { it * -2 },
                animationSpec = tween(600)
            ),
            exit = shrinkOut(shrinkTowards = Alignment.CenterEnd)
        ) {
            LevelAnimation(nextLevelId = nextLevelId)
        }


        GameOverDialog(windowSize = windowSize, dialogVisibility = gameOver, onChangeDialogVisibility = {
            vm.changeGameOverStatus(false)
        })


    }
}

@Composable
fun QuestionBox(titleHeight: Dp, titleWidth: Dp, question: Question,celebrateAnimation: Boolean) {
    BoxWithConstraints(Modifier.fillMaxSize()) {



        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(
                    y = 8.dp
                )
                .alpha(0.75f)
                .height(titleHeight)
                .widthIn(min = titleWidth)
                .clip(RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50))
                .background(Color.DarkGray), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${question.id}: ${question.question}",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .alpha(0.75f)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(titleHeight)
                .widthIn(min = titleWidth)
                .clip(RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50))
                .background(questionBackgroundColor), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${question.id}: ${question.question}",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = questionTextColor,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

@Composable
fun LevelAnimation(nextLevelId: Int) {
    val height = LocalConfiguration.current.screenHeightDp / 2

    val backgroundColor by remember {
        mutableStateOf(getPuzzleBackgroundColor(nextLevelId))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor), contentAlignment = Alignment.Center
    ) {
        TextWithShadow((nextLevelId).toString(), Modifier, height)
    }
}

@Composable
fun BoxWithConstraintsScope.Pause(titleHeight: Dp, onPauseClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(start = 16.dp)
            .padding(top = 8.dp)
            .clickable {
                onPauseClicked()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(titleHeight - 16.dp),
            painter = painterResource(id = R.drawable.pause),
            contentDescription = "pause"
        )
    }
}

@Composable
fun BoxWithConstraintsScope.Menu(
    onDialogClosed: () -> Unit,
    onSkipClicked: () -> Unit,
    onPuzzlesClicked: () -> Unit,
    soundState: Boolean,
    onSoundStateClicked: () -> Unit,
) {
    Dialog(onDismissRequest = { onDialogClosed() }) {
        val rowHeight by remember {
            mutableStateOf(50.dp)
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .size(240.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(menuDialogBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(modifier = Modifier
                .height(rowHeight)
                .fillMaxWidth()
                .clickable {
                    onSkipClicked()
                },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithShadow(
                    text = "Soruyu Atla",
                    Modifier.fillMaxWidth(),
                    22,
                    offset = 2,
                )
            }
            Row(modifier = Modifier
                .height(rowHeight)
                .fillMaxWidth()
                .clickable {
                    onPuzzlesClicked()
                },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithShadow(
                    text = "Tüm Sorular",
                    Modifier.fillMaxWidth(),
                    22,
                    offset = 2,
                )
            }
            Row(modifier = Modifier
                .height(rowHeight)
                .fillMaxWidth()
                .clickable {
                    onSoundStateClicked()
                },
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithShadow(
                    text = "Sesler",
                    Modifier,
                    24,
                    offset = 2,
                    color = if (soundState) Color.White else Color.DarkGray
                )
                Icon(
                    modifier = Modifier.size(rowHeight),
                    painter = painterResource(id = R.drawable.ic_selected),
                    contentDescription = "Oyun Sesi",
                    tint = if (soundState) Color.White else Color.DarkGray
                )
            }
        }
    }

}


@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier,
    fontSize: Int,
    offset: Int = 4,
    color: Color = Color.White
) {
    Box(modifier = Modifier) {
        Text(
            text = text,
            color = Color.DarkGray,
            modifier = modifier
                .offset(
                    x = offset.dp,
                    y = offset.dp
                )
                .alpha(0.75f),
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = text,
            color = color,
            modifier = modifier,
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center
        )
    }
}


fun pickLetterBackgroundColor(
    item: Letter,
    questionType: QuestionType,
    vm: GameViewModel
): Color {
    return when (questionType) {
        QuestionType.DRAGGABLE -> {
            //renkli draggable
            getColorForLetter(item.letter)
        }
        QuestionType.SELECTABLE -> {
            if (vm.getSelectStatus(id = item.id)) getColorForLetter(item.letter) else Color.Black
        }
        QuestionType.TRANSFORMABLE -> {
            getColorForLetter(item.letter)
        }
        QuestionType.DRAGGABLE_TRANSFORMABLE -> {
            //renkli
            getColorForLetter(item.letter)
        }
        QuestionType.DRAGGABLE_SELECTABLE -> {
            //drag renkli, selectable değişken
            if (item.type == Type.DRAGGABLE) {
                getColorForLetter(item.letter)
            } else {
                //selectable
                if (vm.getSelectStatus(id = item.id)) getColorForLetter(item.letter) else Color.Black
            }
        }
        QuestionType.TRANSFORMABLE_SELECTABLE -> {
            if (item.type == Type.SELECTABLE) {
                if (vm.getSelectStatus(id = item.id)) getColorForLetter(item.letter) else Color.Black
            } else {
                getColorForLetter(item.letter)
            }
        }
        QuestionType.MIX -> {
            return when (item.type) {
                Type.DRAGGABLE -> {
                    getColorForLetter(item.letter)
                }
                Type.TRANSFORMABLE -> {
                    getColorForLetter(item.letter)
                }
                Type.SELECTABLE -> {
                    if (vm.getSelectStatus(id = item.id)) getColorForLetter(item.letter) else Color.Black
                }
            }
        }
    }
}

fun getColorForLetter(letter: String): Color {
    return when (letter) {
        "A" -> {
            A_Letter_Color
        }
        "B" -> {
            B_Letter_Color
        }
        "C" -> {
            C_Letter_Color
        }
        "Ç" -> {
            C2_Letter_Color
        }
        "D" -> {
            D_Letter_Color
        }
        "E" -> {
            E_Letter_Color
        }
        "F" -> {
            F_Letter_Color
        }
        "G" -> {
            G_Letter_Color
        }
        "Ğ" -> {
            G2_Letter_Color
        }
        "H" -> {
            H_Letter_Color
        }
        "I" -> {
            I_Letter_Color
        }
        "İ" -> {
            I2_Letter_Color
        }
        "J" -> {
            J_Letter_Color
        }
        "K" -> {
            K_Letter_Color
        }
        "L" -> {
            L_Letter_Color
        }
        "M" -> {
            M_Letter_Color
        }
        "N" -> {
            N_Letter_Color
        }
        "O" -> {
            O2_Letter_Color
        }
        "Ö" -> {
            O2_Letter_Color
        }
        "P" -> {
            P_Letter_Color
        }
        "R" -> {
            R_Letter_Color
        }
        "S" -> {
            S_Letter_Color
        }
        "Ş" -> {
            S2_Letter_Color
        }
        "T" -> {
            T_Letter_Color
        }
        "U" -> {
            U_Letter_Color
        }
        "Ü" -> {
            U2_Letter_Color
        }
        "V" -> {
            V_Letter_Color
        }
        "Y" -> {
            Y_Letter_Color
        }
        "Z" -> {
            Z_Letter_Color
        }
        "Q" -> {
            Q_Letter_Color
        }
        "W" -> {
            W_Letter_Color
        }
        "Z" -> {
            Z_Letter_Color
        }
        else -> {
            A_Letter_Color
        }
    }
}

fun getLetterIcon(item: Letter): Int {
    println("$item  ve  ${item.type.name}")
    return when (item.type) {
        Type.DRAGGABLE -> {
            R.drawable.ic_draggable
        }
        Type.SELECTABLE -> {
            if (item.selected) {
                R.drawable.ic_selected
            } else {
                R.drawable.ic_deselected
            }

        }
        Type.TRANSFORMABLE -> {
            R.drawable.ic_transform
        }
    }
}

fun play(playNote: Int, context: Context) {
    when (playNote) {
        1 -> {
            note1Sound(context)
        }
        2 -> {
            note2Sound(context)
        }
        3 -> {
            note3Sound(context)
        }
        4 -> {
            note4Sound(context)
        }
        5 -> {
            note5Sound(context)
        }
        6 -> {
            note6Sound(context)
        }
        7 -> {
            note7Sound(context)
        }
        8 -> {
            note8Sound(context)
        }
        9 -> {
            note9Sound(context)
        }
        10 -> {
            note10Sound(context)
        }
        11 -> {
            note11Sound(context)
        }
        12 -> {
            note12Sound(context)
        }
        100 -> {
            //select
            selectSound(context)
        }
        101 -> {
            //deselect
            deselectSound(context)
        }
        200 -> {
            //transform1
            transform1Sound(context)
        }
        201 -> {
            //transform1
            transform2Sound(context)
        }
        202 -> {
            //transform1
            transform3Sound(context)
        }
        999 -> {
            //win
            win1Sound(context)
        }
        else -> {}
    }
}


