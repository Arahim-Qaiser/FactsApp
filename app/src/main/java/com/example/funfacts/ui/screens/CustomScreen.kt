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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.funfacts.data.entities.CustomFactEntity
import com.example.funfacts.ui.theme.Gray40
import com.example.funfacts.ui.theme.Green40
import com.example.funfacts.ui.theme.Typography

@Composable
fun InputCard(text: String, onTextChange: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
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
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
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
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {onDelete(fact)}) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun CustomScreen(
    viewModel: CustomViewModel = viewModel(factory = CustomViewModel.Factory),
    onBack: () -> Unit
) {
    val text by viewModel.inputText.collectAsState()
    val facts by viewModel.facts.collectAsState(initial = emptyList())

    CustomScreenContent(
        text = text,
        facts = facts,
        onBack = onBack,
        onTextChange = { viewModel.onTextChange(it) },
        onAddFact = { viewModel.addFact() },
        onDeleteFact = { fact -> viewModel.deleteFact(fact) }
    )
}

@Composable
fun CustomScreenContent(
    text: String,
    facts: List<CustomFactEntity>,
    onBack: () -> Unit,
    onTextChange: (String) -> Unit,
    onAddFact: () -> Unit,
    onDeleteFact: (CustomFactEntity) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = Gray40,
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
                    containerColor = Green40,
                    contentColor = Color.Black
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
            .padding(top = 32.dp, start = 8.dp, end = 8.dp)
            .height(64.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(28.dp),
            ).padding(8.dp)
    ) {

        IconButton(onClick = onBack,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.Black,
                containerColor = Green40,
            ),
            modifier = Modifier.padding(start = 4.dp),
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,

            )
        }
        Text(
            text = "Custom Facts.xy",
            style = Typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
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
        onDeleteFact = {}
    )
}
