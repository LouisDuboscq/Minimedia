package com.lduboscq.minimedia.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.lduboscq.minimedia.DriverFactory
import com.lduboscq.minimedia.cache.SqlLiteMediaDB
import com.lduboscq.minimedia.createDatabase
import com.lduboscq.minimedia.domain.GetDetailMedia
import com.lduboscq.minimedia.domain.GetMedias
import com.lduboscq.minimedia.domain.MediaRepository
import com.lduboscq.minimedia.presentation.DetailViewModel
import com.lduboscq.minimedia.presentation.HomeViewModel
import com.lduboscq.minimedia.remote.HttpMediaApi

class MainActivity : AppCompatActivity() {

    private val database = createDatabase(DriverFactory(this))
    private val storyQueries = database.storyQueries
    private val videoQueries = database.videoQueries
    private val sportQueries = database.sportQueries
    private val mediaApi = HttpMediaApi.instance
    private val mediaDB = SqlLiteMediaDB(storyQueries, videoQueries, sportQueries)
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
