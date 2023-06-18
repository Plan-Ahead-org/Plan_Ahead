package com.palone.planahead

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.palone.planahead.data.ScreensProperties
import com.palone.planahead.screens.home.HomeScreenViewModel
import com.palone.planahead.screens.home.ui.HomeScreen
import com.palone.planahead.screens.taskEdit.TaskEditViewModel
import com.palone.planahead.screens.taskEdit.ui.TaskEditScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlanAheadApp(homeScreenViewModel: HomeScreenViewModel, taskEditViewModel: TaskEditViewModel) {
    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navHostController,
        startDestination = ScreensProperties.HomeScreen.route
    ) {
        composable(ScreensProperties.HomeScreen.route) {
            HomeScreen(viewModel = homeScreenViewModel, navHostController = navHostController)
        }
        composable(ScreensProperties.TaskEditScreen.route) {
            TaskEditScreen(viewModel = taskEditViewModel, navHostController = navHostController)
        }
    }

}