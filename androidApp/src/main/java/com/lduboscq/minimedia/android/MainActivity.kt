package com.lduboscq.minimedia.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lduboscq.minimedia.cache.InMemoryMediaDB
import com.lduboscq.minimedia.domain.GetDetailMedia
import com.lduboscq.minimedia.domain.GetMedias
import com.lduboscq.minimedia.domain.MediaRepository
import com.lduboscq.minimedia.presentation.DetailViewModel
import com.lduboscq.minimedia.presentation.HomeViewModel
import com.lduboscq.minimedia.remote.HttpMediaApi

class MainActivity : AppCompatActivity() {

    private val mediaApi = HttpMediaApi.instance
    private val mediaDB = InMemoryMediaDB()
    private val mediaRepository = MediaRepository(mediaDB, mediaApi)
    val getMedias = GetMedias(mediaRepository)
    private val vm = HomeViewModel(getMedias)
    private val getDetailMedia = GetDetailMedia(mediaRepository)
    private val detailVM = DetailViewModel(getDetailMedia)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniMediaTheme {
                MiniMediaNavGraph(
                    homeViewModel = vm,
                    detailViewModel = detailVM
                )
            }
        }
    }
}
