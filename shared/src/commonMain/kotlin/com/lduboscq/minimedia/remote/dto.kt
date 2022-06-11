package com.lduboscq.minimedia.remote

import kotlinx.serialization.Serializable

@Serializable
data class MediaApiResponse(
    val videos: List<VideoDto>,
    val stories: List<StoryDto>,
)

@Serializable
data class VideoDto(
    val id: Long,
    val title: String,
    val thumb: String,
    val date: Double,
    val sport: SportDto,
    val url: String,
    val views: Long
)

@Serializable
data class SportDto(
    val id: Long,
    val name: String
)

@Serializable
data class StoryDto(
    val id: Long,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val sport: SportDto,
    val author: String
)
