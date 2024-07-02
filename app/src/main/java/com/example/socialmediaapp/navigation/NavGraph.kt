package com.example.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialmediaapp.screens.AddPostScreen
import com.example.socialmediaapp.screens.BottomNavigation
import com.example.socialmediaapp.screens.HomeScreen
import com.example.socialmediaapp.screens.LoginScreen
import com.example.socialmediaapp.screens.NotificationScreen
import com.example.socialmediaapp.screens.OtherUserScreen
import com.example.socialmediaapp.screens.ProfileScreen
import com.example.socialmediaapp.screens.SearchScreen
import com.example.socialmediaapp.screens.SignUpScreen
import com.example.socialmediaapp.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.Splash.routes) {

        composable(Routes.Home.routes) {
            HomeScreen(navController)
        }

        composable(Routes.Search.routes) {
            SearchScreen(navController)
        }

        composable(Routes.AddPost.routes) {
            AddPostScreen(navController)
        }

        composable(Routes.Notification.routes) {
            NotificationScreen()
        }

        composable(Routes.Profile.routes) {
            ProfileScreen(navController)
        }

        composable(Routes.Splash.routes) {
            SplashScreen(navController)
        }

        composable(Routes.BottomNav.routes) {
            BottomNavigation(navController)
        }

        composable(Routes.Login.routes) {
            LoginScreen(navController)
        }

        composable(Routes.SignUp.routes) {
            SignUpScreen(navController)
        }

        composable(Routes.OtherUser.routes) {
            val data = it.arguments?.getString("data")
            OtherUserScreen(navController, data)
        }
    }

}