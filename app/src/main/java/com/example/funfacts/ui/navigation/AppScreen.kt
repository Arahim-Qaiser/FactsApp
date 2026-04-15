package com.example.funfacts.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreen : NavKey {

    @Serializable
    object Home : AppScreen()

    @Serializable
    object CustomFacts : AppScreen()
}