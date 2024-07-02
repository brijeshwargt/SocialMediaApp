package com.example.socialmediaapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediaapp.model.PostModel
import com.example.socialmediaapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.storage
import com.google.rpc.context.AttributeContext.Auth
import java.util.UUID

class ProfileViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    val postRef = database.getReference("posts")
    val userRef = database.getReference("users")

    private val _posts = MutableLiveData(listOf<PostModel>())
    val posts: LiveData<List<PostModel>> get() = _posts
    private val _users = MutableLiveData(PostModel())
    val users: LiveData<PostModel> get() = _users

    fun fetchUser(uid: String) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(PostModel::class.java)
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchPosts (uid: String) {
        postRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = snapshot.children.mapNotNull {
                    it.getValue(PostModel::class.java)
                }
                _posts.postValue(postList.reversed())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}