package com.taka.runejournal.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taka.runejournal.feature.timeline.ui.TimelineScreen
import com.taka.runejournal.core.ui.AboutScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.value
    ) {
        composable(Route.Home.value) {
            TimelineScreen(
                onAboutClick = {
                    navController.navigate(Route.About.value)
                }
            )
        }

        composable(Route.About.value) {
            AboutScreen()
        }
    }
}