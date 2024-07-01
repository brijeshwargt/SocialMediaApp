package com.example.socialmediaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediaapp.model.PostModel
import com.example.socialmediaapp.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    val users = database.getReference("users")

    private var _users = MutableLiveData<List<UserModel>>()
    var userList: LiveData<List<UserModel>> = _users

    init {
        fetchUsers {
            _users.value = it
        }
    }

    private fun fetchUsers(onResult: (List<UserModel>) -> Unit) {

        users.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<UserModel>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(UserModel::class.java)
                    result.add(post!!)
                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromPost(post: PostModel, onResult:(UserModel) -> Unit) {
        database.getReference("users").child(post.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


}