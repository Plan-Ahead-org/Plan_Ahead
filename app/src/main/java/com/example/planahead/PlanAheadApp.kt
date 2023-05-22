package com.example.planahead

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import com.example.planahead.data.ScreensProperties
import com.example.planahead.screens.home.ui.HomeScreen
import com.example.planahead.screens.home.HomeScreenViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlanAheadApp(homeScreenViewModel: HomeScreenViewModel) {
    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navHostController,
        startDestination = ScreensProperties.HomeScreen.route
    ) {
        composable(ScreensProperties.HomeScreen.route){
            HomeScreen(viewModel = homeScreenViewModel, navHostController = navHostController)
        }
        composable("a"){
            Text(text = "test")
        }
    }

}