package com.voyvo.dragdrop.screens.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.voyvo.dragdrop.GameViewModel
import com.voyvo.dragdrop.navigation.Screen
import com.voyvo.dragdrop.util.WindowSize
import com.voyvo.dragdrop.util.WindowType
import com.voyvo.harfiyen.R
import com.voyvo.harfiyen.ui.theme.menuDialogBackground
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(vm : GameViewModel, navHostController: NavHostController,windowSize: WindowSize,onRateApp: () -> Unit) {

    val gameOver by vm.gameOver.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.homebg),
            contentScale = ContentScale.Crop,
            contentDescription = "Oyun Ana Ekranı"
        )
        PlayButton() {
           navHostController.navigate(Screen.Game.route)
        }

        GameOverDialog(windowSize = windowSize, dialogVisibility = gameOver, onChangeDialogVisibility = {
            vm.changeGameOverStatus(false)
        })
    }
}

@Composable
fun PlayButton(onPlayButtonClicked: () -> Unit) {
    AnimatedVisibility(visible = true,
        enter = slideInVertically(initialOffsetY = { it * -2 }, animationSpec = tween(600)),
        exit = slideOutVertically(targetOffsetY = {it * -4}, animationSpec = tween(600))
    ) {

        val width = (LocalConfiguration.current.screenWidthDp / 2).dp
        val height = (LocalConfiguration.current.screenHeightDp / 6).dp
        val textSize = (LocalConfiguration.current.screenHeightDp / 6).dp - 10.dp

        Box(
            modifier = Modifier
                .size(
                    width = width,
                    height = height
                )
                .clickable {
                    onPlayButtonClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_playbuttonbg),
                contentDescription = "BAŞLA",
                contentScale = ContentScale.Crop
            )
            Text(
                text = "BAŞLA",
                fontSize = dpToSp(dp = textSize),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun GameOverDialog(windowSize: WindowSize,
                   dialogVisibility : Boolean,
                   onChangeDialogVisibility : (Boolean) -> Unit) {
    val height = (LocalConfiguration.current.screenHeightDp * 0.8f).dp
    val width = (LocalConfiguration.current.screenHeightDp * 0.9f).dp

    val buttonTextSize by remember {
        mutableStateOf(if (windowSize.height == WindowType.Expanded) 26.sp else 14.sp)
    }

    val buttonWidth = (width * 0.45f)
    val buttonHeight = (height * 0.16f)

    AnimatedVisibility(visible = dialogVisibility) {

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), contentAlignment = Alignment.Center){
                Image(modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop,painter = painterResource(id = R.drawable.gameover), contentDescription = "Oyun Bitti")
                Column(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = {  }, colors = ButtonDefaults.buttonColors(
                        menuDialogBackground)) {
                        Text(text = "Uygulamaya Oy Ver", fontSize = buttonTextSize, color = Color.White, fontFamily = FontFamily.Monospace)
                    }

                    Button(onClick = { onChangeDialogVisibility(false) }, colors = ButtonDefaults.buttonColors(
                        menuDialogBackground)) {
                        Text(text = "Kapat", fontSize = buttonTextSize, color = Color.White, fontFamily = FontFamily.Monospace)
                }
            }

        }
    }
}



@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }