package com.example.socialmediaapp.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.socialmediaapp.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Text(text = "splash")
    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.navigate(Routes.BottomNav.routes)
    }
}