package com.example.socialmediaapp.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.socialmediaapp.R
import com.example.socialmediaapp.navigation.Routes
import com.example.socialmediaapp.utils.SharedPref
import com.example.socialmediaapp.viewmodel.AddPostViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddPostScreen(navHostController: NavHostController) {

    val postViewModel: AddPostViewModel = viewModel()
    val isPosted by postViewModel.isPosted.observeAsState(false)

    val context = LocalContext.current

    var postContent by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {

        }
    }

    LaunchedEffect(isPosted) {
        if (isPosted) {
            postContent = ""
            imageUri = null
            Toast.makeText(context, "Post Added", Toast.LENGTH_SHORT).show()

            navHostController.navigate(Routes.AddPost.routes) {
                popUpTo(navHostController.graph.startDestinationId) {
                    inclusive = true
                }
            }

        }
    }

    Scaffold(
        topBar = { AddPostTopBar(navHostController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = SharedPref.getImage(context)),
                        contentDescription = "user logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = SharedPref.getBio(context),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(end = 120.dp)
                    )
                    IconButton(onClick = {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionToRequest)
                        }

                    }) {
                        if (imageUri == null) {
                            Icon(
                                painter = painterResource(id = R.drawable.attachment),
                                contentDescription = "attach file icon",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "attach file icon",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        imageUri = null
                                    }
                            )
                        }
                    }
                }


            }
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            modifier = Modifier.size(250.dp),
                            contentDescription = "uploaded photo",
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    OutlinedTextField(
                        value = postContent,
                        onValueChange = { postContent = it },
                        label = {
                            Text(text = "Post Content", fontFamily = FontFamily.Monospace)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                    )

                    TextButton(
                        onClick = { postContent = "" },
                        modifier = Modifier
                            .align(alignment = Alignment.End)
                            .padding(end = 10.dp)
                    ) {
                        Text(
                            text = "Clear",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "Anyone can reply !", modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 20.dp),
                        fontSize = 14.sp
                    )

                    if (postContent.isNotEmpty()) {
                        OutlinedButton(onClick = {
                            if (imageUri == null) {
                                postViewModel.saveData(
                                    postContent,
                                    FirebaseAuth.getInstance().currentUser!!.uid,
                                    ""
                                )
                            } else {
                                postViewModel.saveImage(
                                    postContent,
                                    FirebaseAuth.getInstance().currentUser!!.uid,
                                    imageUri!!
                                )
                            }
                        }
                        ) {
                            Text(
                                text = "Post",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostTopBar(navHostController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Add Post",
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navHostController.navigate(Routes.Home.routes) {
                    popUpTo(navHostController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "back arrow")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}