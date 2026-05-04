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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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

    // Observe the repository to keep favorited state in sync across screens
    val isFavorited: StateFlow<Boolean> = combine(_fact, customRepository.allFacts) { currentFact, allCustomFacts ->
        if (currentFact == null) false
        else allCustomFacts.any { it.text == currentFact.text }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage: SharedFlow<String> = _snackbarMessage.asSharedFlow()

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

    fun toggleFavorite() {
        val currentFact = _fact.value ?: return
        if (currentFact.id.isBlank() || currentFact.text == "Error fetching fact") return

        viewModelScope.launch {
            try {
                if (isFavorited.value) {
                    customRepository.deleteFactByText(currentFact.text)
                    _snackbarMessage.emit("Fact removed from favorites!")
                } else {
                    customRepository.addFact(currentFact.text)
                    _snackbarMessage.emit("Fact saved to favorites!")
                }
            } catch (e: Exception) {
                _snackbarMessage.emit("Operation failed.")
            }
        }
    }
}
