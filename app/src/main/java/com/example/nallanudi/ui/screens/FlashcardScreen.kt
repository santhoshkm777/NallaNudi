package com.example.nallanudi.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nallanudi.data.Word
import com.example.nallanudi.ui.NallaNudiViewModel

@Composable
fun FlashcardScreen(viewModel: NallaNudiViewModel) {
    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
    var currentIndex by remember { mutableIntStateOf(0) }
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "FlashcardRotation"
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (searchResults.isNotEmpty() && currentIndex < searchResults.size) {
                val word = searchResults[currentIndex]
                
                Text(
                    text = "Card ${currentIndex + 1} of ${searchResults.size}",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(0.7f)
                        .graphicsLayer {
                            rotationY = rotation
                            cameraDistance = 12f * density
                        }
                        .clickable { rotated = !rotated },
                    contentAlignment = Alignment.Center
                ) {
                    if (rotation <= 90f) {
                        FlashcardSide(text = word.english, title = "English")
                    } else {
                        Box(modifier = Modifier.graphicsLayer { rotationY = 180f }) {
                            FlashcardSide(text = word.kannada, title = "Kannada", color = MaterialTheme.colorScheme.secondaryContainer)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (currentIndex > 0) {
                                currentIndex--
                                rotated = false
                            }
                        },
                        enabled = currentIndex > 0
                    ) {
                        Text("Previous")
                    }
                    Button(
                        onClick = {
                            if (currentIndex < searchResults.size - 1) {
                                currentIndex++
                                rotated = false
                            }
                        },
                        enabled = currentIndex < searchResults.size - 1
                    ) {
                        Text("Next")
                    }
                }
            } else {
                Text("No words available for flashcards. Try searching first.")
            }
        }
    }
}

@Composable
fun FlashcardSide(text: String, title: String, color: Color = MaterialTheme.colorScheme.primaryContainer) {
    ElevatedCard(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = text,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
