package com.lduboscq.minimedia.domain

sealed class Media {
    data class Video(
        val id: Long,
        val title: String,
        val thumb: String,
        val date: Double,
        val sport: Sport,
        val url: String,
        val views: Int
    ): Media()

    data class Sport(
        val id: Long,
        val name: String
    )

    data class Story(
        val id: Long,
        val title: String,
        val teaser: String,
        val image: String,
        val date: Double,
        val sport: Sport,
        val author: String
    ): Media()
}
