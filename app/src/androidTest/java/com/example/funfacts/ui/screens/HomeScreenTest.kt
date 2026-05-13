package com.example.funfacts.ui.screens

import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.funfacts.data.Fact
import com.google.common.base.CharMatcher.any
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
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
    @Test
    fun homeScreen_clickToggleTheme_callsOnToggleTheme() {
        val onToggleMock: () -> Unit = mock()

        composeTestRule.setContent {
            HomeScreenContent(
                fact = null,
                isLoading = false,
                onFetch = {},
                onNavigateToCustom = {},
                onToggleTheme = onToggleMock,
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = {},

                )
        }
        composeTestRule.onNodeWithContentDescription("Theme Toggle").performClick()

        verify(onToggleMock).invoke()
    }
    @Test
    fun homeScreen_clickFavorite_callsOnFavoriteClick() {
        val onFavoriteMock: () -> Unit = mock()
        val factText = "Honey never spoils."
        val fact = Fact(id = "1", text = factText)
        composeTestRule.setContent {
            HomeScreenContent(
                fact = fact,
                isLoading = false,
                onFetch = {},
                onNavigateToCustom = {},
                onToggleTheme = {},
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = onFavoriteMock,
            )

        }
        composeTestRule.onNodeWithContentDescription("Save to favorites").performClick()

        verify(onFavoriteMock).invoke()

    }
    @Test
    fun homeScreen_clickShare_callsOnShare() {
        val onShareMock: () -> Unit = mock()
        val factText = "Honey never spoils."
        val fact = Fact(id = "1", text = factText)
        composeTestRule.setContent {
            HomeScreenContent(
                fact = fact,
                isLoading = false,
                onFetch = {},
                onNavigateToCustom = {},
                onToggleTheme = {},
                snackbarHostState = SnackbarHostState(),
                isFavorited = false,
                onFavoriteClick = onShareMock,
            )

        }
        composeTestRule.onNodeWithContentDescription("Share on WhatsApp").performClick()

        verify(onShareMock).invoke()

    }


}
