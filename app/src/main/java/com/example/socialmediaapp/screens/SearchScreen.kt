package com.example.socialmediaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.socialmediaapp.itemview.PostItem
import com.example.socialmediaapp.itemview.UserItem
import com.example.socialmediaapp.model.UserModel
import com.example.socialmediaapp.viewmodel.HomeViewModel
import com.example.socialmediaapp.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SearchScreen(navHostController: NavHostController) {

    val searchViewModel: SearchViewModel = viewModel()
    val userList by searchViewModel.userList.observeAsState(null)

    var searchName by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Search",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
        TextField(value = searchName, onValueChange = {
            searchName = it
        }, leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search icon",
                modifier = Modifier.size(30.dp)
            )
        }, textStyle = TextStyle(
            fontSize = 18.sp, fontWeight = FontWeight.W400, fontFamily = FontFamily.Monospace
        ), colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ), modifier = Modifier.fillMaxWidth()
        )

        LazyColumn() {

            val filterItems = userList?.filter {
                it.userName.contains(searchName, ignoreCase = true) ||
                        it.bio.contains(searchName, ignoreCase = true)
            }

            items(filterItems ?: emptyList()) { pairs ->
                UserItem(
                    users = pairs, navHostController
                )

            }
        }
    }

}