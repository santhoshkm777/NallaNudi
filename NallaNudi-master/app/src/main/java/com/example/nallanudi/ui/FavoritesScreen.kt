package com.example.nallanudi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.nallanudi.R
import com.example.nallanudi.ui.components.TermCard

@Composable
fun FavoritesScreen(viewModel: NallaNudiViewModel) {
    val favorites by viewModel.favoriteTerms.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.my_list),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text(text = stringResource(R.string.no_favorites))
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favorites) { word ->
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
