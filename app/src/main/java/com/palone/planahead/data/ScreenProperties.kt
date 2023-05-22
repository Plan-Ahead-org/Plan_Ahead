package com.palone.planahead.data

sealed class ScreensProperties(val route: String, val name: String, val selected: Boolean = false) {
    object HomeScreen : ScreensProperties("home_screen", "Home Screen", true)
}