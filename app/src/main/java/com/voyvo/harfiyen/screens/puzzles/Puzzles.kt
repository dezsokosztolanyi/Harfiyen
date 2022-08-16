package com.voyvo.dragdrop.screens.puzzles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.voyvo.dragdrop.GameViewModel
import com.voyvo.dragdrop.Question
import com.voyvo.dragdrop.rquestions
import com.voyvo.dragdrop.screens.game.TextWithShadow
import com.voyvo.harfiyen.R
import com.voyvo.harfiyen.ui.theme.*

@Composable
fun Puzzles(viewModel: GameViewModel, navHostController: NavHostController) {
    var rowHeight by remember {
        mutableStateOf(80.dp)
    }
    val level by viewModel.maxLevel.collectAsState()


    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(rquestions) { question ->
                if (question.id <= level) {
                    KnownQuestion(question = question, height = rowHeight, onLevelClicked = {
                        viewModel.goSelectedLevel(it)
                        navHostController.popBackStack()
                    })
                } else {
                    UnknownQuestion(level = question.id, height = rowHeight)
                }
            }
        }

        this.CancelButton(rowHeight = rowHeight) {
            //onCancelClicked
            navHostController.popBackStack()
        }
    }

}

@Composable
fun BoxScope.CancelButton(rowHeight : Dp,onCancelClicked: () -> Unit) {
    Icon(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(start = 16.dp)
            .padding(top = 8.dp)
            .size(rowHeight * 0.8f)
            .clickable {
                onCancelClicked()
            }
     , painter = painterResource(id = R.drawable.ic_cancel), tint = Color.White, contentDescription = "Geri Dön")
}

@Composable
fun KnownQuestion(question: Question, height: Dp,onLevelClicked: (Int) -> Unit) {
    var puzzleBgColor by remember {
        mutableStateOf(getPuzzleBackgroundColor(question.id))
    }

    Row(
        Modifier
            .fillMaxWidth()
            .height(height)
            .background(puzzleBgColor)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(height))
        TextWithShadow(
            text = "${question.id}: ${question.question}",
            modifier = Modifier,
            fontSize = 30,
            offset = 2
        )
        Icon(modifier = Modifier
            .size(height)
            .clickable {
                onLevelClicked(question.id)
            }, painter = painterResource(id = R.drawable.ic_playlevel), contentDescription = "Bu Seviyeden Devam Et")
    }
}

@Composable
fun UnknownQuestion(level: Int, height: Dp) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.DarkGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithShadow(text = "$level: ???", modifier = Modifier, fontSize = 30)
    }
}

fun getPuzzleBackgroundColor(id: Int): Color {
    val id1 = listOf(A_Letter_Color, B_Letter_Color, C_Letter_Color)
    val id2 = listOf(C2_Letter_Color, D_Letter_Color, E_Letter_Color)
    val id3 = listOf(F_Letter_Color, G_Letter_Color, G2_Letter_Color)
    val id4 = listOf(H_Letter_Color, I_Letter_Color, I2_Letter_Color)
    val id5 = listOf(J_Letter_Color, K_Letter_Color, L_Letter_Color)
    val id6 = listOf(M_Letter_Color, N_Letter_Color, U_Letter_Color)
    val id7 = listOf(U2_Letter_Color, P_Letter_Color, R_Letter_Color)
    val id8 = listOf(S2_Letter_Color, S_Letter_Color, T_Letter_Color)
    val id9 = listOf(O_Letter_Color, O2_Letter_Color, V_Letter_Color)
    val id0 = listOf(Y_Letter_Color, Z_Letter_Color, X_Letter_Color)

    return when (Math.abs(id) % 10) {
        0 -> {
            id0.random()
        }
        1 -> {
            id1.random()
        }
        2 -> {
            id2.random()
        }
        3 -> {
            id3.random()
        }
        4 -> {
            id4.random()
        }
        5 -> {
            id5.random()
        }
        6 -> {
            id6.random()
        }
        7 -> {
            id7.random()
        }
        8 -> {
            id8.random()
        }
        9 -> {
            id9.random()
        }
        else -> {
            id1.random()
        }
    }
}
