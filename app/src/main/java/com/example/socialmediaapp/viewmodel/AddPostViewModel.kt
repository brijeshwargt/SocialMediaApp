package com.example.socialmediaapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediaapp.model.PostModel
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import java.util.UUID

class AddPostViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    val postRef = database.getReference("posts")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("posts/${UUID.randomUUID()}.jpg")

    private val _isPosted = MutableLiveData<Boolean>()
    var isPosted: LiveData<Boolean> = _isPosted

    fun saveImage(
        post: String,
        userId: String,
        imageUri: Uri
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {

            imageRef.downloadUrl.addOnSuccessListener {
                saveData(post, userId, it.toString())
            }
        }
    }

    fun saveData(
        post: String,
        userId: String,
        image: String
    ) {

        val postData = PostModel(post, image, userId, System.currentTimeMillis().toString())

        postRef.child(postRef.push().key!!).setValue(postData)
            .addOnSuccessListener {

                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }

    }

}