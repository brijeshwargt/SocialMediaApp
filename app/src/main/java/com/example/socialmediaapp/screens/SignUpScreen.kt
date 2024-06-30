package com.example.socialmediaapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen() {

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isErrorInEmail by remember { mutableStateOf(false) }
    var isErrorInPassword by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Sign Up...",
            fontWeight = FontWeight.Bold,
            fontSize = 70.sp,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(100.dp))
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
        OutlinedTextField(
            value = bio ,
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
            visualTransformation = if(isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Default.Lock else null

            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            border = BorderStroke(.5.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 120.dp)
        ) {
            Text(text = "Sign Up", fontSize = 25.sp, fontWeight = FontWeight.Thin)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(text = "Already a user? ")
            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { },
                fontWeight = FontWeight.W600
            )
        }
    }
}