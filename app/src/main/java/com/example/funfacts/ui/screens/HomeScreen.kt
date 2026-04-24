package com.example.funfacts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.funfacts.data.Fact
import com.example.funfacts.ui.theme.Green40
import com.example.funfacts.ui.theme.Typography

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToCustom: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val fact by viewModel.fact.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    HomeScreenContent(
        fact = fact,
        isLoading = isLoading,
        onFetch = { viewModel.fetchRandomFact() },
        onNavigateToCustom = onNavigateToCustom,
        onToggleTheme = onToggleTheme
    )
}

@Composable
fun HomeScreenContent(
    fact: Fact?,
    isLoading: Boolean,
    onFetch: () -> Unit,
    onNavigateToCustom: () -> Unit,
    onToggleTheme: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HomeTopBar(onNavigateToCustom = onNavigateToCustom,
                onToggleTheme = onToggleTheme)
        },
        bottomBar = {
            FooterText()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            BannerCard()

            Spacer(modifier = Modifier.weight(0.5f))

            FactCard(
                fact = fact,
                isLoading = isLoading,
                onFetch = onFetch
            )

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

@Composable
fun HomeTopBar(onNavigateToCustom: () -> Unit, onToggleTheme: () -> Unit) {
    val isDark = MaterialTheme.colorScheme.surface == Color(0xFF1E1E1E)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(28.dp))
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Facts.xy",
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.weight(1f))
        IconButton(onClick = onToggleTheme,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = Modifier.padding(start = 4.dp),
        ){
            Icon(
                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.Bedtime,
                contentDescription = "Theme Toggle",
                tint = MaterialTheme.colorScheme.onPrimary,
                )
        }
        IconButton(onClick = onNavigateToCustom,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = Modifier.padding(start = 4.dp),
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Forward",
                tint = MaterialTheme.colorScheme.onPrimary,
                )
        }
    }
}

@Composable
fun BannerCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Rounded.Lightbulb,
                contentDescription = "Idea",
                modifier = Modifier.padding(end = 16.dp)
            )

            Text(
                text = "Discover random, worthless facts you absolutely need.",
                style = Typography.titleLarge,
            )
        }
    }
}

@Composable
fun FactCard(fact: Fact?, onFetch: () -> Unit, isLoading: Boolean) {
    val scrollState = rememberScrollState()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
                else {
                    Text(
                        text = fact?.text ?: "No fact yet",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .weight(1f)
                            .verticalScroll(scrollState),
                        style = Typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onFetch,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Fetch Fun Fact",
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun FooterText() {
    Text(
        text = "built for test",
        textAlign = TextAlign.Center,
        style = Typography.bodySmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyFact = Fact(
        id = "123",
        text = "Bananas are actually berries, but strawberries aren't.",
        source = "123",
        sourceUrl = "123",
        language = "en",
        permalink = "123"
    )

    HomeScreenContent(
        fact = dummyFact,
        isLoading = false,
        onFetch = {},
        onNavigateToCustom = {},
        onToggleTheme = {}
    )
}
