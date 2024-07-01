package com.example.socialmediaapp.model

import android.net.Uri
import androidx.core.net.toUri

data class UserModel(
   val email: String = "",
   val password: String = "",
   val name: String = "",
   val bio: String = "",
   val userName: String = "",
   val imageUrl: String = "",
   val uid: String? = ""
) {
}