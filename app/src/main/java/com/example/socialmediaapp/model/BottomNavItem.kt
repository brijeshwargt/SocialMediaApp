package com.example.socialmediaapp.model

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)