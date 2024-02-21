package com.example.exploracerca.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exploracerca.pantallas.home.Home
import com.example.exploracerca.pantallas.login.LoginScreen

@Composable
fun ExploraCercaNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ExploraCercaScreens.LoginScreen.name
    ) {

        composable(ExploraCercaScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(ExploraCercaScreens.HomeScreen.name) {
            Home(navController = navController)
        }
    }

}