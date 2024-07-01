package com.example.socialmediaapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaapp.model.BottomNavItem
import com.example.socialmediaapp.navigation.Routes

@Composable
fun BottomNavigation(navController: NavHostController) {

    val navController1 = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController1 = navController1) }
    ) {paddingValues ->
        NavHost(navController = navController1, startDestination = Routes.Home.routes, modifier = Modifier.padding(paddingValues)) {
            composable(Routes.Home.routes) {
                HomeScreen()
            }
            composable(Routes.Search.routes) {
                SearchScreen()
            }
            composable(Routes.AddPost.routes) {
                AddPostScreen(navController1)
            }
            composable(Routes.Notification.routes) {
                NotificationScreen()
            }
            composable(Routes.Profile.routes){
                ProfileScreen()
            }
        }

    }

}
@Composable
fun BottomBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(

        BottomNavItem(
            title = "home", route = Routes.Home.routes, icon = Icons.Rounded.Home
        ),

        BottomNavItem(
            title = "search", route = Routes.Search.routes, icon = Icons.Rounded.Search
        ),

        BottomNavItem(
            title = "add_post", route = Routes.AddPost.routes, icon = Icons.Rounded.Add
        ),

        BottomNavItem(
            title = "notification",
            route = Routes.Notification.routes,
            icon = Icons.Rounded.Notifications
        ),

        BottomNavItem(
            title = "profile", route = Routes.Profile.routes, icon = Icons.Rounded.Person
        ),

        )

    BottomAppBar {
        list.forEach {

            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController1.navigate(it.route){
                        popUpTo(navController1.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) }
            )
        }
    }

}