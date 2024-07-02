package com.example.socialmediaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.socialmediaapp.itemview.PostItem
import com.example.socialmediaapp.navigation.Routes
import com.example.socialmediaapp.utils.SharedPref
import com.example.socialmediaapp.viewmodel.AuthViewModel
import com.example.socialmediaapp.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherUserScreen(navHostController: NavHostController, uid: String?) {

    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val profileViewModel: ProfileViewModel = viewModel()
    val posts by profileViewModel.posts.observeAsState(null)
    val users by profileViewModel.users.observeAsState(null)
    val followerList by profileViewModel.followerList.observeAsState(null)
    val followingList by profileViewModel.followingList.observeAsState(null)


    profileViewModel.fetchPosts(uid!!)
    profileViewModel.fetchUser(uid)
    profileViewModel.getFollowers(uid)
    profileViewModel.getFollowing(uid)

    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null) {
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            navHostController.navigate(Routes.Login.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "User Profile", fontSize = 27.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (followingList != null && followerList!!.isNotEmpty() && followerList!!.contains(currentUserId))"Following" else "Follow",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                if (currentUserId != "") {
                                    profileViewModel.followUsers(uid, currentUserId)
                                }
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = users!!.bio,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = users!!.userName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = users!!.name)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${followerList!!.size}")

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${followingList!!.size}")
                }

                Image(
                    painter = rememberAsyncImagePainter(model = users!!.imageUrl),
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 25.dp, end = 15.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            LazyColumn {
                items(posts ?: emptyList()) { pair ->
                    PostItem(
                        post = pair,
                        users = users!!,
                        navHostController = navHostController,
                        userId = SharedPref.getUserName(context)
                    )
                }
            }
        }

    }
}