package com.voyvo.dragdrop.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntSize
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.voyvo.dragdrop.GameViewModel
import com.voyvo.dragdrop.screens.puzzles.Puzzles
import com.voyvo.dragdrop.screens.game.GameScreen
import com.voyvo.dragdrop.screens.splash.SplashScreenDemo
import com.voyvo.dragdrop.util.WindowSize

@ExperimentalAnimationApi
@Composable
fun NavGraph(viewModel: GameViewModel, windowSize : WindowSize) {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Up, animationSpec = tween(1000))
        },
        exitTransition = {
            shrinkOut(shrinkTowards = Alignment.Center, targetSize = { size -> IntSize(0,size.height) }, animationSpec = tween(1000,300))
        }

    ) {

        composable(Screen.Puzzles.route){
            Puzzles(viewModel,navController)
        }
        composable(Screen.Game.route) {
            GameScreen(viewModel,navController,windowSize)
        }
        composable(Screen.Splash.route) {
            SplashScreenDemo(navController)

        }

    }
}