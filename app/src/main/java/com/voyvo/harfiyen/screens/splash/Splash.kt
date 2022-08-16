package com.voyvo.dragdrop.screens.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.voyvo.harfiyen.R
import com.voyvo.dragdrop.navigation.Screen
import com.voyvo.harfiyen.ui.theme.menuDialogBackground
import kotlinx.coroutines.delay

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SplashScreenDemo(navHostController: NavHostController) {

//    val splashImage by remember {
//        mutableStateOf(pickRandomSplashImage())
//    }

    var animState by remember{
        mutableStateOf(AnimState.START)
    }

    LaunchedEffect(key1 = true, block = {
        delay(400)
        animState = AnimState.MID
        delay(900)
        animState = AnimState.MID2
        delay(1200)
        animState = AnimState.END
        delay(800)
        navHostController.popBackStack()
        navHostController.navigate(Screen.Game.route)
    })



    val transition = updateTransition(targetState = animState, label = "")

    val scale by transition.animateFloat(transitionSpec = {
        when(animState){
            AnimState.START -> {
                tween(durationMillis = 0)
            }
            AnimState.MID -> {
                tween(durationMillis = 500, delayMillis = 100)
            }
            AnimState.MID2 -> {
                tween(durationMillis = 700, delayMillis = 100)
            }
            AnimState.END -> {
                tween(durationMillis = 700)
            }
        }
    },targetValueByState = {
        when(animState){
            AnimState.START -> {
                1f
            }
            AnimState.MID -> {
                1.2f
            }
            AnimState.MID2 -> {
                1.2f
            }
            AnimState.END -> {
                0f
            }
        }
    }, label = "")

    val rotate by transition.animateFloat(transitionSpec = {
        when(animState){
            AnimState.START -> {
                tween(durationMillis = 0)
            }
            AnimState.MID -> {
                tween(durationMillis = 500)
            }
            AnimState.MID2 -> {
                tween(durationMillis = 700)
            }
            AnimState.END -> {
                tween(durationMillis = 700)
            }
        }
    },targetValueByState = {
        when(animState){
            AnimState.START -> {
                0f
            }
            AnimState.MID -> {
                30f
            }
            AnimState.MID2 -> {
                -30f
            }
            AnimState.END -> {
                0f
            }
        }
    }, label = "")


    Box(
        Modifier
            .fillMaxSize()
            .background(menuDialogBackground), contentAlignment = Alignment.Center){
        Image(modifier = Modifier
            .rotate(rotate)
            .size(240.dp)
            .scale(scale), painter = painterResource(id = R.drawable.ic_splash_bg), contentDescription = "")
    }

}

//fun pickRandomSplashImage() : Int{
//    val splashImageList = listOf(
//        R.drawable.puzzle_splash
//    )
//    return splashImageList.random()
//}

enum class AnimState{
    START,MID,MID2,END
}