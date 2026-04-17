package com.example.funfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.funfacts.ui.navigation.AppNav
import com.example.funfacts.ui.screens.HomeScreen
import com.example.funfacts.ui.screens.ThemeViewModel
import com.example.funfacts.ui.theme.FunFactsTheme

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




