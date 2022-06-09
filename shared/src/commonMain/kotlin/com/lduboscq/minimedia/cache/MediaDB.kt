package com.lduboscq.minimedia.cache

import com.lduboscq.minimedia.domain.Media

interface MediaDB {
    fun save(medias: List<Media>)
    fun get(): List<Media>
}

class InMemoryMediaDB: MediaDB {

    private val storage: MutableList<Media> = mutableListOf()

    override fun save(medias: List<Media>) {
        storage.addAll(medias)
    }

    override fun get(): List<Media> {
        return storage
    }

}