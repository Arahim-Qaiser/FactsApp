package com.example.funfacts.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.funfacts.data.entities.CustomFactEntity
import com.example.funfacts.data.repository.CustomFactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FactSnackbarEvent(val message: String, val isSuccess: Boolean) {
    ADDED("Fact added successfully!", true),
    REMOVED("Fact removed!", true),
    ERROR("Operation failed!", false)
}

@HiltViewModel
class CustomViewModel @Inject constructor(
    private val repository: CustomFactRepository
) : ViewModel() {

    val facts = repository.allFacts

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _snackbarEvent = MutableSharedFlow<FactSnackbarEvent>()
    val snackbarEvent: SharedFlow<FactSnackbarEvent> = _snackbarEvent.asSharedFlow()


    fun onTextChange(newText: String) {
        val sanitizedText = newText.replace(Regex("\n{3,}"), "\n\n")

        if (sanitizedText.length <= 500) {
            _inputText.value = sanitizedText
        }
    }

    fun addFact() {
        viewModelScope.launch {
            try {
                if (_inputText.value.isNotBlank()) {
                    repository.addFact(_inputText.value)
                    _inputText.value = ""
                    _snackbarEvent.emit(FactSnackbarEvent.ADDED)
                }
            } catch (e: Exception) {
                _snackbarEvent.emit(FactSnackbarEvent.ERROR)
            }
        }
    }

    fun deleteFact(fact: CustomFactEntity) {
        viewModelScope.launch {
            try {
                repository.deleteFact(fact)
                _snackbarEvent.emit(FactSnackbarEvent.REMOVED)
            } catch (e: Exception) {
                _snackbarEvent.emit(FactSnackbarEvent.ERROR)
            }
        }
    }

}
