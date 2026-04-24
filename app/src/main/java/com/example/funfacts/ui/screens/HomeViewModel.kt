package com.example.funfacts.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.funfacts.data.Fact
import com.example.funfacts.data.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FactRepository
) : ViewModel() {

    private val _fact = MutableStateFlow<Fact?>(null)
    val fact: StateFlow<Fact?> = _fact

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchRandomFact()
    }

    fun fetchRandomFact() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _fact.value = repository.getRandomFacts()
            } catch (e: Exception) {
                _fact.value = Fact(
                    id = "",
                    text = "Error fetching fact",
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}
