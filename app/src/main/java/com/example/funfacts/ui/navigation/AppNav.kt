package com.example.funfacts.ui.navigation//package com.example.funfacts.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.funfacts.ui.screens.CustomScreen
import com.example.funfacts.ui.screens.HomeScreen

@Composable
fun AppNav(onToggleTheme: () -> Unit) {
    val backStack = rememberNavBackStack(AppScreen.Home)

    val paddingValues = null
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AppScreen.Home> {
                HomeScreen(
                    onNavigateToCustom = { backStack.add(AppScreen.CustomFacts) },
                    onToggleTheme = onToggleTheme
                )
            }
            entry<AppScreen.CustomFacts>
                {
               CustomScreen(onBack = { backStack.removeLastOrNull() })
            }
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },

    )
}