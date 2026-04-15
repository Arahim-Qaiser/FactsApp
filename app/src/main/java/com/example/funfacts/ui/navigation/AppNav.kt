package com.example.funfacts.ui.navigation//package com.example.funfacts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.funfacts.ui.screens.CustomScreen
import com.example.funfacts.ui.screens.HomeScreen

@Composable
fun AppNav() {
    val backStack = rememberNavBackStack(AppScreen.Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AppScreen.Home> {
                HomeScreen(
                    onNavigateToCustom = { backStack.add(AppScreen.CustomFacts) }
                )
            }
            entry<AppScreen.CustomFacts> {
               CustomScreen(onBack = { backStack.removeLastOrNull() })
            }
        }
    )
}