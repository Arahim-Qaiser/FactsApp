package com.example.funfacts.ui.screens

import com.example.funfacts.data.Fact
import com.example.funfacts.data.FactRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: FactRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @Test
    fun `init calls fetchRandomFact and updates state`() = runTest {
        val mockFact = Fact(id = "1", text = "Shrimps heart is in its head")
        
        // Use whenever to handle suspending call properly
        whenever(repository.getRandomFacts()).thenAnswer {
            mockFact
        }

        // Initialize ViewModel (triggers init block)
        viewModel = HomeViewModel(repository)
        
        // runCurrent starts the coroutine and proceeds to the first suspension point
        runCurrent()
        
        assertThat(viewModel.isLoading.value).isTrue()

        // advanceUntilIdle executes everything until completion
        advanceUntilIdle()

        assertThat(viewModel.fact.value).isEqualTo(mockFact)
        assertThat(viewModel.isLoading.value).isFalse()
    }

    @Test
    fun `fetchRandomFact updates error state on failure`() = runTest {
        whenever(repository.getRandomFacts()).thenThrow(RuntimeException("Network Error"))

        viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        assertThat(viewModel.fact.value?.text).isEqualTo("Error fetching fact")
        assertThat(viewModel.isLoading.value).isFalse()
    }
}
