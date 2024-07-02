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

class HomeViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val post = database.getReference("posts")

    private var _postsAndUsers = MutableLiveData<List<Pair<PostModel, UserModel>>>()
    var postsAndUsers: LiveData<List<Pair<PostModel, UserModel>>> = _postsAndUsers

    init {
        fetchPostsAndUsers {
            _postsAndUsers.value = it
        }
    }

    private fun fetchPostsAndUsers(onResult: (List<Pair<PostModel, UserModel>>) -> Unit) {

        post.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<Pair<PostModel, UserModel>>()

                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(PostModel::class.java)
                    if (post != null) {
                        fetchUserFromPost(post) { user ->
                            result.add(0, post to user)
                            if (result.size == snapshot.childrenCount.toInt()) {
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromPost(post: PostModel, onResult: (UserModel) -> Unit) {

        database.getReference("users").child(post.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.getValue(UserModel::class.java)
                    if (user!= null) {
                        onResult(user)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


}