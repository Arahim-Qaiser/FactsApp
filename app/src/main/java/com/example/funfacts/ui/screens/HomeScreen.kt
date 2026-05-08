package com.example.funfacts.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.funfacts.data.Fact
import com.example.funfacts.ui.theme.Typography

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToCustom: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val fact by viewModel.fact.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isFavorited by viewModel.isFavorited.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    HomeScreenContent(
        fact = fact,
        isLoading = isLoading,
        isFavorited = isFavorited,
        onFetch = { viewModel.fetchRandomFact() },
        onFavoriteClick = { viewModel.toggleFavorite() },
        onNavigateToCustom = onNavigateToCustom,
        onToggleTheme = onToggleTheme,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun HomeScreenContent(
    fact: Fact?,
    isLoading: Boolean,
    isFavorited: Boolean,
    onFetch: () -> Unit,
    onFavoriteClick: () -> Unit,
    onNavigateToCustom: () -> Unit,
    onToggleTheme: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            HomeTopBar(onNavigateToCustom = onNavigateToCustom,
                onToggleTheme = onToggleTheme)
        },
        bottomBar = {
            FooterText()
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val scopeHeight = maxHeight
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .heightIn(scopeHeight)
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
                    isFavorited = isFavorited,
                    onFetch = onFetch,
                    onFavoriteClick = onFavoriteClick
                )

                Spacer(modifier = Modifier.weight(0.5f))
            }
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
fun FactCard(
    fact: Fact?,
    onFetch: () -> Unit,
    onFavoriteClick: () -> Unit,
    isLoading: Boolean,
    isFavorited: Boolean
) {
    val scrollState = rememberScrollState()
    val favoriteColor by animateColorAsState(
        targetValue = if (isFavorited) Color.Red else MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.6f),
        label = "favoriteColor"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Favorite Button at Top Right
            IconButton(
                onClick = onFavoriteClick,
                enabled = !isLoading && fact != null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Save to favorites",
                    tint = favoriteColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 24.dp), // Space for top-right icon
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                    } else {
                        Text(
                            text = fact?.text ?: "No fact yet",
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .padding(horizontal = 32.dp),
                            style = Typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onTertiary,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fetch Button at Bottom Center
                Button(
                    onClick = onFetch,
                    enabled = !isLoading,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = if (isLoading) "Fetching..." else "Fetch Fun Fact",
                        style = Typography.bodySmall
                    )
                }
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
    )

    HomeScreenContent(
        fact = dummyFact,
        isLoading = false,
        isFavorited = false,
        onFetch = {},
        onFavoriteClick = {},
        onNavigateToCustom = {},
        onToggleTheme = {},
        snackbarHostState = SnackbarHostState()
    )
}
