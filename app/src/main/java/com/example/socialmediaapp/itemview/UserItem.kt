package com.example.socialmediaapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.socialmediaapp.model.UserModel
import com.example.socialmediaapp.navigation.Routes
import okhttp3.Route

@Composable
fun UserItem(
    users: UserModel,
    navHostController: NavHostController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable {
                val routes = users.uid?.let { Routes.OtherUser.routes.replace("{data}", it) }
                navHostController.navigate(routes!!)
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = users.imageUrl),
            contentDescription = "profile photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = users.userName, fontWeight = FontWeight.SemiBold)
            Text(
                text = users.bio,
                fontWeight = FontWeight.Thin
            ) // some misplaced of name and bio LOL
        }
    }
}
