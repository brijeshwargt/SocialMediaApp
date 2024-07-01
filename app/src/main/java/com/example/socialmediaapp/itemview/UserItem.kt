package com.example.socialmediaapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun UserItem(
    users: UserModel,
    navHostController: NavHostController
) {
    OutlinedCard(
        shape = RoundedCornerShape(30),
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = users.imageUrl),
                contentDescription = "profile photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(30))
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
}
