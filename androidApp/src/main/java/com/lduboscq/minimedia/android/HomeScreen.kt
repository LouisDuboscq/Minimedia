@file:OptIn(ExperimentalMaterial3Api::class)

package com.lduboscq.minimedia.android

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.lduboscq.minimedia.convertDateToLong
import com.lduboscq.minimedia.convertLongToTime
import com.lduboscq.minimedia.domain.Media
import com.lduboscq.minimedia.presentation.HomeViewModel

@Composable
fun HomeScreen(
    state: HomeViewModel.State,
    onClickArticle: (Long) -> Unit,
    //onClickVideo: (String) -> Unit
) {

    if (state.loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .testTag("progress-indicator")
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text("FEATURED", style = LocalTextStyle.current.copy(color = Color.White))
            })
        }
    ) {
        Surface(
            color = Color(0xFFf2f2f2)
        ) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(state.medias) { media ->
                    when (media) {
                        is Media.Video -> VideoItem(
                            media,
                            modifier = Modifier.padding(vertical = 4.dp),
                            onClick = {
                                //onClickVideo(it.url)
                            })
                        is Media.Story -> StoryItem(media,
                            modifier = Modifier.padding(vertical = 4.dp),
                            onClick = { onClickArticle(media.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun VideoItem(
    video: Media.Video,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(video.toString(), modifier = Modifier.clickable { onClick() })
}

@Composable
fun StoryItem(
    story: Media.Story,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .testTag("story-card"),
        onClick = onClick
    ) {
        Column {
            Box(modifier = Modifier.height(220.dp)) {
                AsyncImage(
                    model = story.image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Red)
                        .testTag("story-image"),
                    contentDescription = story.title,
                    contentScale = ContentScale.Crop
                )
                Text(
                    story.sport.name.uppercase(),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .background(Color(0XFF141b4d), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    style = LocalTextStyle.current.copy(color = Color(0XFFFFFFFF))
                )
                /* Spacer(
                     Modifier
                         .align(Alignment.BottomCenter)
                         .height(20.dp)
                         .background(Color.Yellow)
                 )*/
            }
            Text(
                story.title,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0XFF000000)
                )
            )
            Text(
                "By ${story.author} - ${convertLongToTime((story.date * 1000).toLong())}",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                style = LocalTextStyle.current.copy(color = Color(0XFFd8d8d8))
            )
        }
    }
}
