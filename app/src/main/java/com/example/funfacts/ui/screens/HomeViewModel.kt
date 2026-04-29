package com.example.funfacts.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.funfacts.data.Fact
import com.example.funfacts.data.FactRepository
import com.example.funfacts.data.repository.CustomFactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FactRepository,
    private val customRepository: CustomFactRepository
) : ViewModel() {

    private val _fact = MutableStateFlow<Fact?>(null)
    val fact: StateFlow<Fact?> = _fact

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isFavorited = MutableStateFlow(false)
    val isFavorited: StateFlow<Boolean> = _isFavorited.asStateFlow()

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage: SharedFlow<String> = _snackbarMessage.asSharedFlow()

    init {
        fetchRandomFact()
    }

    fun fetchRandomFact() {
        viewModelScope.launch {
            _isLoading.value = true
            _isFavorited.value = false
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

    fun saveToFavorites() {
        val currentFact = _fact.value
        if (currentFact != null && currentFact.id.isNotBlank() && !_isFavorited.value) {
            viewModelScope.launch {
                try {
                    customRepository.addFact(currentFact.text)
                    _isFavorited.value = true
                    _snackbarMessage.emit("Fact saved to favorites!")
                } catch (e: Exception) {
                    _snackbarMessage.emit("Failed to save fact.")
                }
            }
        }
    }
}
