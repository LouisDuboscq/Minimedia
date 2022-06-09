package com.lduboscq.minimedia.domain

class GetMedias(private val mediaRepository: IMediaRepository) {

    suspend operator fun invoke(): List<Media> {
        return mediaRepository.getMedias().sortedByDescending {
            when (it) {
                is Media.Video -> it.date
                is Media.Story -> it.date
            }
        }
    }
}
