package com.lduboscq.minimedia

import com.lduboscq.minimedia.cache.MediaDB
import com.lduboscq.minimedia.domain.Media
import com.lduboscq.minimedia.domain.MediaRepository
import com.lduboscq.minimedia.remote.MediaApi
import com.lduboscq.minimedia.remote.MediaApiResponse
import com.lduboscq.minimedia.remote.SportDto
import com.lduboscq.minimedia.remote.StoryDto
import com.lduboscq.minimedia.remote.VideoDto
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class MediaRepositoryTest {

    private val medias: List<Media> = listOf(
        Media.Story(
            id = 1,
            title = "a story",
            teaser = "a teaser",
            image = "an image",
            date = 1588110350.85,
            sport = Media.Sport(1, "Football"),
        ),
        Media.Video(
            id = 2,
            title = "a video",
            date = 1488133445.007,
            sport = Media.Sport(2, "Tennis"),
            thumb = "tennis thumb",
            url = "an url"
        )
    )

    @Test
    fun noItemInDBShouldCallApi() = runBlocking {
        var apiCalled = 0
        val mediaDB = object : MediaDB {
            override fun save(medias: List<Media>) {}
            override fun get(): List<Media> = emptyList()
        }

        val mediaApi = object : MediaApi {
            override suspend fun fetch(): MediaApiResponse {
                apiCalled++
                return MediaApiResponse(emptyList(), emptyList()) // irrelevant, just apiCalled is tested
                 /*MediaApiResponse(
                    videos = listOf(
                        VideoDto(
                            id = 2,
                            title = "a video",
                            date = 1488133445.007,
                            sport = SportDto(2, "Tennis"),
                            thumb = "tennis thumb",
                            url = "an url"
                        )
                    ),
                    stories = listOf(
                        StoryDto(
                            id = 1,
                            title = "a story",
                            teaser = "a teaser",
                            image = "an image",
                            date = 1588110350.85,
                            sport = SportDto(1, "Football"),
                        )
                    )
                )*/
            }
        }

        val res = MediaRepository(mediaDB, mediaApi).getMedias()
      //  assertEquals(medias, res)
        assertEquals(1, apiCalled)
    }

    @Test
    fun dbNotEmptyShouldNotCallApi() = runBlocking {
        val mediaDB = object : MediaDB {
            override fun save(medias: List<Media>) {}
            override fun get(): List<Media> = medias
        }

        val mediaApi = object : MediaApi {
            override suspend fun fetch(): MediaApiResponse {
                throw Error()
            }
        }

        val res = MediaRepository(mediaDB, mediaApi).getMedias()
        assertEquals(medias, res)
    }
}