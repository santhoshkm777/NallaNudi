package com.example.nallanudi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nallanudi.ui.FavoritesScreen
import com.example.nallanudi.ui.HomeScreen
import com.example.nallanudi.ui.NallaNudiViewModel
import com.example.nallanudi.ui.screens.FlashcardScreen
import com.example.nallanudi.ui.screens.ProfileScreen
import com.example.nallanudi.ui.screens.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: NallaNudiViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route) {
            HomeScreen(viewModel)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(viewModel)
        }
        composable(Screen.Flashcards.route) {
            FlashcardScreen(viewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}
