package com.lduboscq.minimedia.domain

import com.lduboscq.minimedia.cache.MediaDB
import com.lduboscq.minimedia.remote.MediaApi
import com.lduboscq.minimedia.remote.SportDto
import com.lduboscq.minimedia.remote.StoryDto
import com.lduboscq.minimedia.remote.VideoDto

interface IMediaRepository {
    suspend fun getMedias(): List<Media>
}

class MediaRepository(
    private val mediaDB: MediaDB,
    private val mediaApi: MediaApi
) : IMediaRepository {
    override suspend fun getMedias(): List<Media> {
        if (mediaDB.get().isEmpty()) {
            val mediaResponse = mediaApi.fetch()
            mediaDB.save(mediaResponse.stories.map { it.toDomain() } + mediaResponse.videos.map { it.toDomain() })
        }

        return mediaDB.get()
    }
}

private fun StoryDto.toDomain(): Media.Story {
    return Media.Story(
        id = id,
        title = title,
        teaser = teaser,
        image = image,
        date = date,
        sport = sport.toDomain(),
        author = author
    )
}

private fun VideoDto.toDomain(): Media.Video {
    return Media.Video(
        id = id,
        title = title,
        date = date,
        sport = sport.toDomain(),
        thumb = thumb,
        url = url,
        views = views,
    )
}

private fun SportDto.toDomain(): Media.Sport {
    return Media.Sport(
        id = id,
        name = name
    )
}
