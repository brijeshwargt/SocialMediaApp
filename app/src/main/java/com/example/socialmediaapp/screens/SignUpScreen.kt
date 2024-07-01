package com.example.socialmediaapp.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.socialmediaapp.R
import com.example.socialmediaapp.navigation.Routes
import com.example.socialmediaapp.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navHostController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isErrorInEmail by remember { mutableStateOf(false) }
    var isErrorInPassword by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

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

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {

        }
    }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            navHostController.navigate(Routes.BottomNav.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (!isSystemInDarkTheme()) Color.LightGray else Color.Transparent)
    ) {
        item {
            Text(
                text = "Sign Up...",
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp,
                letterSpacing = 2.sp,
//            color = MaterialTheme.colorScheme.primary
            )
        }
//        Spacer(modifier = Modifier.height(10.dp))
        item {

            Image(
                painter = if (imageUri == null) painterResource(id = R.drawable.woman)
                else rememberAsyncImagePainter(model = imageUri),
                contentDescription = "person",
                contentScale = ContentScale.Crop,
                alpha = 0.9f,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        }
//        Spacer(modifier = Modifier.height(4.dp))
        item {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add icon",
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "Add Photo")
            }
        }
//        Spacer(modifier = Modifier.height(20.dp))
        item {

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(text = "Name")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Person, contentDescription = "person_icon")
                },
                shape = RoundedCornerShape(40),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )
        }

        item {

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = {
                    Text(text = "Username")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "username icon")
                },
                shape = RoundedCornerShape(40),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
            )
        }

        item {

            OutlinedTextField(
                value = bio,
                onValueChange = {
                    bio = it
                },
                label = {
                    Text(text = "Bio")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Face, contentDescription = "bio icon")
                },
                shape = RoundedCornerShape(40),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
            )
        }

        item {

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isErrorInEmail = !email.matches(emailRegex)
                },
                label = {
                    Text(text = "Email")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Email, contentDescription = "email_icon")
                },
                shape = RoundedCornerShape(40),
                isError = isErrorInEmail,
                supportingText = {
                    if (isErrorInEmail) {
                        Text(
                            text = "Invalid email",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
            )
        }

        item {

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isErrorInPassword = password.length < 8
                },
                label = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Lock, contentDescription = "password_icon")
                },
                shape = RoundedCornerShape(40),
                isError = isErrorInPassword,
                supportingText = {
                    if (isErrorInPassword) {
                        Text(
                            text = "Length is short",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible) Icons.Default.Lock else null

                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedButton(
                onClick = {
                    if (name.isEmpty() || email.isEmpty() || bio.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(
                            context, "Please fill all details", Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (imageUri == null) {
                        Toast.makeText(context, "Upload Photo", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.singup(
                            email,
                            password,
                            bio,
                            username,
                            name,
                            imageUri!!,
                            context
                        )
                    }
                },
                border = BorderStroke(.5.dp, MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 120.dp)
            ) {
                Text(text = "Sign Up", fontSize = 25.sp, fontWeight = FontWeight.Thin)
            }
        }

        item {

            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Already a user? ")
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navHostController.navigate(Routes.Login.routes) {
                            popUpTo(navHostController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}
