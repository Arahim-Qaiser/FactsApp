package com.example.funfacts.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.funfacts.data.entities.CustomFactEntity
import com.example.funfacts.ui.theme.Typography

@Composable
fun InputCard(text: String, onTextChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .padding(horizontal = 16.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,

                placeholder = { Text("Enter a fact") },

                shape = RoundedCornerShape(28.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun FactsList(facts: List<CustomFactEntity>, onDelete: (CustomFactEntity) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(facts) { fact ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = fact.text,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(onClick = {onDelete(fact)}) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun CustomScreen(
    viewModel: CustomViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val text by viewModel.inputText.collectAsState()
    val facts by viewModel.facts.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(
                message = event.message
            )
        }
    }

    CustomScreenContent(
        text = text,
        facts = facts,
        onBack = onBack,
        onTextChange = { viewModel.onTextChange(it) },
        onAddFact = { viewModel.addFact() },
        onDeleteFact = { fact -> viewModel.deleteFact(fact) },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun CustomScreenContent(
    text: String,
    facts: List<CustomFactEntity>,
    onBack: () -> Unit,
    onTextChange: (String) -> Unit,
    onAddFact: () -> Unit,
    onDeleteFact: (CustomFactEntity) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CustomTopBar(onBack = onBack)
      },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            InputCard(
                text = text,
                onTextChange = { onTextChange(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onAddFact,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Add Fact",
                    style = Typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            FactsList(
                facts = facts,
                onDelete = onDeleteFact
            )


            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

@Composable
fun CustomTopBar(onBack: () -> Unit,)
{
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .height(64.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(28.dp),
            ).padding(8.dp)
    ) {

        IconButton(onClick = onBack,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterStart),
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimary,

            )
        }
        Text(
            text = "Custom Facts.xy",
            style = Typography.titleLarge,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CustomScreenPreview() {
    val dummyFacts = listOf(
        CustomFactEntity(id = 1, text = "My name is Khan"),
        CustomFactEntity(id = 2, text = "Cats sleep 70% of their lives"),
        CustomFactEntity(id = 3, text = "Bananas are berries")
    )

    CustomScreenContent(
        text = "",
        facts = dummyFacts,
        onBack = {},
        onTextChange = {},
        onAddFact = {},
        onDeleteFact = {},
        snackbarHostState = SnackbarHostState()
    )
}
