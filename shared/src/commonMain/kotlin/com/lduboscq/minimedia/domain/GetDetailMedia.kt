package com.lduboscq.minimedia.domain

class GetDetailMedia(
    private val mediaRepository: MediaRepository
) {

    suspend operator fun invoke(id: Long): Media? {
        return mediaRepository.getMedias()
            .filterIsInstance<Media.Story>()
            .find { it.id == id }
    }
}
