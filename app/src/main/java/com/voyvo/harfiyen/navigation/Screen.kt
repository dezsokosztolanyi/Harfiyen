package com.voyvo.dragdrop.navigation

sealed class Screen(val route : String){
    object Splash : Screen("splash_screen")
    object Game : Screen("game_screen")
    object Puzzles : Screen("puzzles_screen")
    object Home : Screen("home_screen")
}
