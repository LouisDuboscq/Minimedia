package com.lduboscq.minimedia.cache

import com.lduboscq.minimedia.SportQueries
import com.lduboscq.minimedia.Sport_Entity
import com.lduboscq.minimedia.StoryQueries
import com.lduboscq.minimedia.Story_Entity
import com.lduboscq.minimedia.VideoQueries
import com.lduboscq.minimedia.Video_Entity
import com.lduboscq.minimedia.domain.Media

interface MediaDB {
    fun save(medias: List<Media>)
    fun get(): List<Media>
}

class InMemoryMediaDB : MediaDB {

    private val storage: MutableList<Media> = mutableListOf()

    override fun save(medias: List<Media>) {
        storage.addAll(medias)
    }

    override fun get(): List<Media> {
        return storage
    }
}

class SqlLiteMediaDB(
    private val storyQueries: StoryQueries,
    private val videoQueries: VideoQueries,
    private val sportQueries: SportQueries,
) : MediaDB {

    override fun save(medias: List<Media>) {
        medias.forEach { media ->
            when (media) {
                is Media.Story -> {
                    sportQueries.insertOrReplace(
                        Sport_Entity(media.sport.id, media.sport.name)
                    )
                    storyQueries.insertOrReplace(media.toEntity())
                }
                is Media.Video -> {
                    sportQueries.insertOrReplace(
                        Sport_Entity(media.sport.id, media.sport.name)
                    )
                    videoQueries.insertOrReplace(media.toEntity())
                }
            }
        }
    }

    override fun get(): List<Media> {
        return storyQueries.selectAll().executeAsList().map { it.toStory() } +
                videoQueries.selectAll().executeAsList().map { it.toVideo() }
    }

    private fun Story_Entity.toStory(): Media.Story {
        return Media.Story(
            id = id,
            title = title,
            teaser = teaser,
            image = image,
            date = date,
            sport = sportQueries.selectById(sport_id).executeAsOne().toSport(),
            author = author
        )
    }

    private fun Video_Entity.toVideo(): Media.Video {
        return Media.Video(
            id = id,
            title = title,
            date = date,
            sport = sportQueries.selectById(sport_id).executeAsOne().toSport(),
            thumb = thumb,
            url = url,
            views = views,
        )
    }

    private fun Sport_Entity.toSport(): Media.Sport {
        return Media.Sport(
            id = id,
            name = name
        )
    }

    private fun Media.Story.toEntity(): Story_Entity {
        return Story_Entity(
            id = id,
            title = title,
            date = date,
            teaser = teaser,
            image = image,
            author = author,
            sport_id = sport.id
        )
    }

    private fun Media.Video.toEntity(): Video_Entity {
        return Video_Entity(
            id = id,
            title = title,
            date = date,
            thumb = thumb,
            url = url,
            views = views,
            sport_id = sport.id
        )
    }
}
