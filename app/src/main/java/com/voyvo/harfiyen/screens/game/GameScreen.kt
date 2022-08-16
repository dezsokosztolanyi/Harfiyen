package com.voyvo.dragdrop.screens.game

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.voyvo.dragdrop.GameViewModel
import com.voyvo.dragdrop.util.WindowSize
import com.voyvo.dragdrop.util.rememberWindowSize

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    navHostController: NavHostController,
    windowSize: WindowSize
) {
    val context = LocalContext.current

    val message by viewModel.message.collectAsState()
    val question by viewModel.currentQuestion.collectAsState()
    val gameState by viewModel.gameState.collectAsState()
    val playNote by viewModel.notes.collectAsState()
    val enabled by viewModel.enabled.collectAsState()

    val celebrateAnim by viewModel.celebrateAnim.collectAsState()


    LaunchedEffect(key1 = playNote, block = {
        play(playNote,context)
    })

    GameBoard(
        vm = viewModel,
        modifier = Modifier,
        gameState = gameState,
        question = question,
        enabled = enabled,
        navHostController = navHostController,
        letterClicked = { viewModel.letterClicked(it) },
        celebrateAnimation = celebrateAnim,
        windowSize = windowSize)
}
