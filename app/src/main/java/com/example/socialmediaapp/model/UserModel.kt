package com.example.socialmediaapp.model

data class UserModel(
   val email: String = "",
   val password: String = "",
   val name: String = "",
   val userName: String = "",
   val bio: String = "",
   val imageUrl: String = "",
   val uid: String? = ""
)