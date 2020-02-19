package com.example.bottomsheet.viewmodel

import androidx.compose.Model

sealed class Screens {
    object Home : Screens()
    object Second : Screens()
}
@Model
object Status {
    var currentScreen: Screens =
        Screens.Home
}
fun navigateTo(destination: Screens) {
    Status.currentScreen = destination
}
