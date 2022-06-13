@file:OptIn(ExperimentalMaterial3Api::class)

package com.lduboscq.minimedia.android

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.lduboscq.minimedia.convertLongToTime
import com.lduboscq.minimedia.domain.Media
import com.lduboscq.minimedia.presentation.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeViewModel.State,
    onClickArticle: (Long) -> Unit,
    onClickVideo: (Media.Video) -> Unit,
    effects: Flow<HomeViewModel.Effect>,
    onDispose: (Media.Video) -> Unit,
    navigateBack: () -> Unit
) {

    var fullScreenVideo: Media.Video? by remember { mutableStateOf(null) }

    if (state.loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .testTag("progress-indicator")
        )
    }

    BackHandler {
        if (fullScreenVideo != null) {
            fullScreenVideo = null
        } else {
            navigateBack()
        }
    }

    if (fullScreenVideo != null) {
        FullScreenVideo(video = requireNotNull(fullScreenVideo))
    } else {
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
                                    // onClickVideo(media)
                                    fullScreenVideo = media
                                },
                                effects = effects,
                                onDispose = onDispose
                            )
                            is Media.Story -> StoryItem(media,
                                modifier = Modifier.padding(vertical = 4.dp),
                                onClick = { onClickArticle(media.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FullScreenVideo(video: Media.Video) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.Builder()
                    .setUri(video.url)
                    .setMediaId(video.id.toString())
                    .setTag(video.id)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setDisplayTitle(video.title)
                            .build()
                    )
                    .build()
            )

            playWhenReady = true
            this.prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        }
    )
}

@Composable
fun VideoItem(
    video: Media.Video,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    effects: Flow<HomeViewModel.Effect>,
    onDispose: (Media.Video) -> Unit
) {

    val context = LocalContext.current
    val currentlyPlaying = remember { mutableStateOf(false) }

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.Builder()
                    .setUri(video.url)
                    .setMediaId(video.id.toString())
                    .setTag(video.id)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setDisplayTitle(video.title)
                            .build()
                    )
                    .build()
            )

            this.prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
            onDispose(video)
        }
    }

    LaunchedEffect(Unit) {
        effects.onEach {
            when (it) {
                is HomeViewModel.Effect.Play -> {
                    if (it.video == video) {
                        currentlyPlaying.value = true
                        exoPlayer.play()
                    }
                }
                is HomeViewModel.Effect.Pause -> {
                    if (it.video == video) {
                        currentlyPlaying.value = false
                        exoPlayer.pause()
                    }
                }
                is HomeViewModel.Effect.ReleaseVideoPlayer -> {
                    if (it.video == video) {
                        currentlyPlaying.value = false
                    }
                }
            }
        }.collect()
    }

    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .testTag("video-card"),
        onClick = { onClick() }
    ) {
        Column {
            Box(modifier = Modifier.height(240.dp)) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            exoPlayer.seekTo(1)
                            useController = false
                        }
                    }
                )

                IconButton(
                    onClick = { onClick() },
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    val icon = if (currentlyPlaying.value) {
                        Icons.Filled.PauseCircleFilled
                    } else {
                        Icons.Filled.PlayCircleFilled
                    }
                    Icon(
                        icon,
                        null,
                        modifier = Modifier
                            .size(100.dp),
                        tint = Color.White
                    )
                }

                Text(
                    video.sport.name.uppercase(),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .background(Color(0XFF141b4d), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    style = LocalTextStyle.current.copy(color = Color(0XFFFFFFFF))
                )
            }
            Text(
                video.title,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0XFF000000)
                )
            )
            Text(
                "${video.views} views",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                style = LocalTextStyle.current.copy(color = Color(0XFFd8d8d8))
            )
        }
    }
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
