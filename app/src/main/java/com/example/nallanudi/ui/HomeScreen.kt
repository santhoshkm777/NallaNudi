package com.example.nallanudi.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nallanudi.R
import com.example.nallanudi.data.Word
import com.example.nallanudi.ui.components.TermCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: NallaNudiViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val wordOfTheDay by viewModel.wordOfTheDay.collectAsState()
    val selectedSubject by viewModel.selectedSubject.collectAsState()

    val subjects = listOf(
        "Science", "Math", "Commerce", "Computer Science", 
        "Family", "Education", "Fruits", "Vegetables", 
        "Animals", "Verbs", "Emotions", "Greetings", "Travel", "Technology"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Modern Search Bar
        SearchBarUI(
            query = searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) },
            onClearQuery = { viewModel.updateSearchQuery("") }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp) // Space for bottom bar
        ) {
            if (searchQuery.isEmpty()) {
                // Word of the Day Section
                item {
                    SectionHeader(title = stringResource(R.string.word_of_the_day))
                    wordOfTheDay?.let { word ->
                        TermCard(
                            word = word,
                            onFavoriteClick = { viewModel.toggleFavorite(word) },
                            onSpeakClick = { viewModel.speak(word) }
                        )
                    }
                }

                // Subject Filters Section
                item {
                    SectionHeader(title = stringResource(R.string.filter_by_subject))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedSubject == null,
                                onClick = { viewModel.selectSubject(null) },
                                label = { Text(stringResource(R.string.all)) },
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                        items(subjects) { subject ->
                            FilterChip(
                                selected = selectedSubject == subject,
                                onClick = { viewModel.selectSubject(subject) },
                                label = { Text(subject) },
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }
                }

                item {
                    SectionHeader(title = stringResource(R.string.glossary))
                }
            } else {
                item {
                    SectionHeader(title = "${stringResource(R.string.search_results)} for \"$searchQuery\"")
                }
            }

            // Results List with Animations
            if (searchResults.isEmpty() && searchQuery.trim().isNotEmpty()) {
                item {
                    NoResultsUI()
                }
            } else {
                items(
                    items = searchResults,
                    key = { it.id }
                ) { word ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        TermCard(
                            word = word,
                            onFavoriteClick = { viewModel.toggleFavorite(word) },
                            onSpeakClick = { viewModel.speak(word) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBarUI(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                Row {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = onClearQuery) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                    IconButton(onClick = { /* Voice Search */ }) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Search")
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun NoResultsUI() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SentimentVeryDissatisfied,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No words found",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Try searching with different terms or check for spelling errors.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
