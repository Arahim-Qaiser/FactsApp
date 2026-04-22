package com.example.funfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.funfacts.ui.navigation.AppNav
import com.example.funfacts.ui.screens.ThemeViewModel
import com.example.funfacts.ui.theme.FunFactsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkTheme.collectAsState()

            FunFactsTheme (darkTheme = isDarkMode){
                AppNav(onToggleTheme = { themeViewModel.toggleTheme() })
                }
            }
        }
    }
