package com.example.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaapp.screens.AddPostScreen
import com.example.socialmediaapp.screens.BottomNavigation
import com.example.socialmediaapp.screens.HomeScreen
import com.example.socialmediaapp.screens.NotificationScreen
import com.example.socialmediaapp.screens.ProfileScreen
import com.example.socialmediaapp.screens.SearchScreen
import com.example.socialmediaapp.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.Splash.routes) {

        composable(Routes.Home.routes) {
            HomeScreen()
        }

        composable(Routes.Search.routes) {
            SearchScreen()
        }

        composable(Routes.AddPost.routes) {
            AddPostScreen()
        }

        composable(Routes.Notification.routes) {
            NotificationScreen()
        }

        composable(Routes.Profile.routes) {
            ProfileScreen()
        }

        composable(Routes.Splash.routes) {
            SplashScreen(navController)
        }

        composable(Routes.BottomNav.routes) {
            BottomNavigation(navController = navController)
        }
    }

}