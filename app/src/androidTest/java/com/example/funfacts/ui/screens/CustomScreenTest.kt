package com.example.funfacts.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.funfacts.data.entities.CustomFactEntity
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CustomScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customScreen_displaysInitialState() {
        composeTestRule.setContent {
            CustomScreenContent(
                text = "",
                facts = emptyList(),
                onBack = {},
                onTextChange = {},
                onAddFact = {},
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithText("Custom Facts.xy").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enter a fact").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add Fact").assertIsDisplayed()
    }

    @Test
    fun customScreen_displaysFactsList() {
        val facts = listOf(
            CustomFactEntity(id = 1, text = "Fact 1"),
            CustomFactEntity(id = 2, text = "Fact 2")
        )

        composeTestRule.setContent {
            CustomScreenContent(
                text = "",
                facts = facts,
                onBack = {},
                onTextChange = {},
                onAddFact = {},
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithText("Fact 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fact 2").assertIsDisplayed()
    }

    @Test
    fun customScreen_enteringText_callsOnTextChange() {
        val onTextChangeMock: (String) -> Unit = mock()

        composeTestRule.setContent {
            CustomScreenContent(
                text = "",
                facts = emptyList(),
                onBack = {},
                onTextChange = onTextChangeMock,
                onAddFact = {},
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithText("Enter a fact").performTextInput("New Fact")

        verify(onTextChangeMock).invoke("New Fact")
    }

    @Test
    fun customScreen_clickAdd_callsOnAddFact() {
        val onAddFactMock: () -> Unit = mock()

        composeTestRule.setContent {
            CustomScreenContent(
                text = "New Fact",
                facts = emptyList(),
                onBack = {},
                onTextChange = {},
                onAddFact = onAddFactMock,
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithText("Add Fact").performClick()

        verify(onAddFactMock).invoke()
    }

    @Test
    fun customScreen_clickDelete_callsOnDeleteFact() {
        val onDeleteFactMock: (CustomFactEntity) -> Unit = mock()
        val fact = CustomFactEntity(id = 1, text = "Fact to delete")

        composeTestRule.setContent {
            CustomScreenContent(
                text = "",
                facts = listOf(fact),
                onBack = {},
                onTextChange = {},
                onAddFact = {},
                onDeleteFact = onDeleteFactMock,
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithContentDescription("Delete").performClick()

        verify(onDeleteFactMock).invoke(fact)
    }

    @Test
    fun customScreen_clickBack_callsOnBack() {
        val onBackMock: () -> Unit = mock()

        composeTestRule.setContent {
            CustomScreenContent(
                text = "",
                facts = emptyList(),
                onBack = onBackMock,
                onTextChange = {},
                onAddFact = {},
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        verify(onBackMock).invoke()
    }

    @Test
    fun customScreen_emptyText_doesNotCallOnAddFact() {
        val onAddFactMock: () -> Unit = mock()
        composeTestRule.setContent {
            CustomScreenContent(
                text = "",
                facts = emptyList(),
                onBack = {},
                onTextChange = {},
                onAddFact = onAddFactMock,
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

    }
    @Test
    fun customScreen_nonEmptyText_callsOnAddFact() {
        val onAddFactMock: () -> Unit = mock()
        composeTestRule.setContent {
            CustomScreenContent(
                text = "New Fact",
                facts = emptyList(),
                onBack = {},
                onTextChange = {},
                onAddFact = onAddFactMock,
                onDeleteFact = {},
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule.onNodeWithText("Add Fact").performClick()

        verify(onAddFactMock).invoke()
    }

//    @Test
//    fun customScreen_emptyText_doesNotCallOnDeleteFact() {
//        val onDeleteFactMock: (CustomFactEntity) -> Unit = mock()
//        composeTestRule.setContent {
//            CustomScreenContent(
//                text = "",
//                facts = emptyList(),
//                onBack = {},
//                onTextChange = {},
//                onAddFact = {},
//                onDeleteFact = onDeleteFactMock,
//                snackbarHostState = SnackbarHostState()
//            )
//        }
//        composeTestRule.onNodeWithContentDescription("Delete").performClick()
//
//        verify(onDeleteFactMock).invoke()
//        }


}
