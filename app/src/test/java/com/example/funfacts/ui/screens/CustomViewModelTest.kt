package com.example.funfacts.ui.screens

import com.example.funfacts.data.entities.CustomFactEntity
import com.example.funfacts.data.repository.CustomFactRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class CustomViewModelTest {

    private lateinit var viewModel: CustomViewModel
    private lateinit var repository: CustomFactRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(CustomFactRepository::class.java)
        `when`(repository.allFacts).thenReturn(flowOf(emptyList()))
        viewModel = CustomViewModel(repository)
    }

    @Test
    fun `onTextChange updates inputText state`() {
        viewModel.onTextChange("New Fact")
        assertThat(viewModel.inputText.value).isEqualTo("New Fact")
    }

    @Test
    fun `addFact calls repository and clears input`() = runTest {
        viewModel.onTextChange("Valid Fact")
        viewModel.addFact()
        
        advanceUntilIdle()

        verify(repository).addFact("Valid Fact")
        assertThat(viewModel.inputText.value).isEmpty()
    }

    @Test
    fun `deleteFact calls repository`() = runTest {
        val fact = CustomFactEntity(id = 1, text = "Fact to delete")
        viewModel.deleteFact(fact)

        advanceUntilIdle()

        verify(repository).deleteFact(fact)
    }
}
