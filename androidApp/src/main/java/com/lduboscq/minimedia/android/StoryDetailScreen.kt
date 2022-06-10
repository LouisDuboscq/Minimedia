package com.lduboscq.minimedia.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lduboscq.minimedia.convertLongToTime
import com.lduboscq.minimedia.presentation.DetailViewModel

@Composable
fun StoryDetailScreen(
    state: DetailViewModel.State,
    navigateBack: () -> Unit
) {
    Column {
        Box(modifier = Modifier.height(220.dp)) {

            AsyncImage(
                model = state.story?.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Red)
                    .testTag("story-image"),
                contentDescription = state.story?.title,
                contentScale = ContentScale.Crop
            )
            Text(
                state.story?.sport?.name?.uppercase() ?: "",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color(0XFF141b4d), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                style = LocalTextStyle.current.copy(color = Color(0XFFFFFFFF))
            )
            IconButton(
                onClick = navigateBack,
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("back-button"),
            ) {
                Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
            ) {
                Icon(Icons.Filled.Share, null, tint = Color.White)
            }
        }

        Text(
            state.story?.title ?: "",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0XFF000000)
            )
        )
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                "By ",
                modifier = Modifier.padding(start = 16.dp),
                style = LocalTextStyle.current.copy(color = Color(0XFFd8d8d8))
            )
            Text(
                "${state.story?.author}",
                modifier = Modifier.padding(start = 2.dp),
                style = LocalTextStyle.current.copy(color = Color(0XFF4eaeff))
            )
        }
        state.story?.date?.let {
            Text(
                convertLongToTime((it * 1000).toLong()),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = LocalTextStyle.current.copy(color = Color(0XFFd8d8d8))
            )
        }

        Text(
            state.story?.teaser ?: "",
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}
