package com.learn.clockdwithcity.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learn.clockdwithcity.view.changetime.ChangeTimeZone
import com.learn.clockdwithcity.view.MainScreen
import com.learn.clockdwithcity.view.navigation.NavRoutes.CHANGE_TIME_SCREEN
import com.learn.clockdwithcity.view.navigation.NavRoutes.MAIN_SCREEN

@Composable
fun AppNavigation(startDestination: String = MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = MAIN_SCREEN) { MainScreen(navController) }
        composable(route = CHANGE_TIME_SCREEN) { ChangeTimeZone() }

    }
}

object NavRoutes {
    const val MAIN_SCREEN = "SplashScreen"
    const val CHANGE_TIME_SCREEN = "ChangeTimeZone"
}