package com.example.socialmediaapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.socialmediaapp.R
import com.example.socialmediaapp.model.PostModel
import com.example.socialmediaapp.model.UserModel

@Composable
fun PostItem(
    post: PostModel,
    users: UserModel,
    navHostController: NavHostController,
    userId: String
) {

    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = users.imageUrl),
                        contentDescription = "profile photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = users.userName,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                    )
                }
                Column {
                    Text(text = "12.12PM")
                    Text(text = "2 July 2024")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = post.post)
            Spacer(modifier = Modifier.height(5.dp))

            if (post.image != "") {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(post.image),
                        contentDescription = "posted image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(300.dp)
                            .clip(RoundedCornerShape(5))
                    )
                }
            }
        }
    }

}

