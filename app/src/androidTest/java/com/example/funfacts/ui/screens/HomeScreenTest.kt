package com.example.funfacts.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.funfacts.data.Fact
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysFactAndBanner() {
        val factText = "Honey never spoils."
        val fact = Fact(id = "1", text = factText)
        val defaultText = "Discover random, worthless facts you absolutely need."
        composeTestRule.setContent {
            HomeScreenContent(
                fact = fact,
                isLoading = false,
                onFetch = {},
                onNavigateToCustom = {},
                onToggleTheme = {},
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = {},



            )
        }
        // Verify banner text
        composeTestRule.onNodeWithText(defaultText).assertIsDisplayed()
        
        // Verify fact text
        composeTestRule.onNodeWithText(factText).assertIsDisplayed()
    }

    @Test
    fun homeScreen_showsLoadingIndicator() {
        composeTestRule.setContent {
            HomeScreenContent(
                fact = null,
                isLoading = true,
                onFetch = {},
                onNavigateToCustom = {},
                onToggleTheme = {},
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = {},

            )
        }

        composeTestRule.onNodeWithText("No fact yet").assertDoesNotExist()
    }

    @Test
    fun homeScreen_clickFetch_callsOnFetch() {
        val onFetchMock: () -> Unit = mock()
        
        composeTestRule.setContent {
            HomeScreenContent(
                fact = null,
                isLoading = false,
                onFetch = onFetchMock,
                onNavigateToCustom = {},
                onToggleTheme = {},
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = {},

            )
        }

        composeTestRule.onNodeWithText("Fetch Fun Fact").performClick()
        
        verify(onFetchMock).invoke()
    }
    
    @Test
    fun homeScreen_clickForward_callsOnNavigateToCustom() {
        val onNavigateMock: () -> Unit = mock()
        
        composeTestRule.setContent {
            HomeScreenContent(
                fact = null,
                isLoading = false,
                onFetch = {},
                onNavigateToCustom = onNavigateMock,
                onToggleTheme = {},
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = {},

            )
        }

        composeTestRule.onNodeWithContentDescription("Forward").performClick()
        
        verify(onNavigateMock).invoke()
    }
}
