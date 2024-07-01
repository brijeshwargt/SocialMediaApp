package com.example.socialmediaapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.socialmediaapp.navigation.Routes
import com.example.socialmediaapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navHostController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            navHostController.navigate(Routes.Login.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop
            }
        }
    }

    Text(
        text = "Profile",
        modifier = Modifier.clickable {
            authViewModel.logout()
        })
}