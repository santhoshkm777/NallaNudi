package com.example.nallanudi.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object WordDetail : Screen("word_detail/{wordId}") {
        fun createRoute(wordId: Int) = "word_detail/$wordId"
    }
    object Favorites : Screen("favorites")
    object Flashcards : Screen("flashcards")
    object Profile : Screen("profile")
}
