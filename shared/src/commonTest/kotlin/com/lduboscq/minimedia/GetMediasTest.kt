package com.lduboscq.minimedia

import com.lduboscq.minimedia.domain.GetMedias
import com.lduboscq.minimedia.domain.IMediaRepository
import com.lduboscq.minimedia.domain.Media
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMediasTest {

    @Test
    fun getMediasCheckDbTest() = runBlocking {
        val list = listOf(
            Media.Story(
                id = 1,
                title = "a story",
                teaser = "a teaser",
                image = "an image",
                date = 1588110350.85,
                sport = Media.Sport(1, "Football"),
            )
        )

        val getMedias = GetMedias(object : IMediaRepository {
            override suspend fun getMedias(): List<Media> = list
        })
        assertEquals(list, getMedias())
    }
}