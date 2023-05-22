package com.palone.planahead

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.palone.planahead.data.ScreensProperties
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.home.ui.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlanAheadApp(homeScreenViewModel: HomeScreenViewModel) {
    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navHostController,
        startDestination = ScreensProperties.HomeScreen.route
    ) {
        composable(ScreensProperties.HomeScreen.route) {
            HomeScreen(viewModel = homeScreenViewModel, navHostController = navHostController)
        }
        composable("a") {
            Text(text = "test")
        }
    }

}