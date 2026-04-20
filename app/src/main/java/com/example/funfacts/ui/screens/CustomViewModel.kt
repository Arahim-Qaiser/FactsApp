package com.example.funfacts.ui.screens

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.funfacts.data.DatabaseProvider
import com.example.funfacts.data.entities.CustomFactEntity
import com.example.funfacts.data.repository.CustomFactRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

enum class FactSnackbarEvent(val message: String, val isSuccess: Boolean) {
    ADDED("Fact added successfully!", true),
    REMOVED("Fact removed!", true),
    ERROR("Operation failed!", false)
}

class CustomViewModel(private val repository: CustomFactRepository) : ViewModel() {

    val facts = repository.allFacts

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _snackbarEvent = MutableSharedFlow<FactSnackbarEvent>()
    val snackbarEvent: SharedFlow<FactSnackbarEvent> = _snackbarEvent.asSharedFlow()

    fun onTextChange(newText: String) {
        _inputText.value = newText
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                val database = DatabaseProvider.getDatabase(application)
                val repository = CustomFactRepository(database.dao())
                CustomViewModel(repository)
            }
        }
    }
}
