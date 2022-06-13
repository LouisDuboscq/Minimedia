package com.lduboscq.minimedia.presentation

import com.lduboscq.minimedia.domain.GetMedias
import com.lduboscq.minimedia.domain.Media
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class HomeViewModel(
    private val getMedias: GetMedias
) {

    data class State(
        val loading: Boolean = false,
        val medias: List<Media> = emptyList(),
        val videoPlaying: Media.Video? = null
    )

    sealed interface Effect {
        data class Play(val video: Media.Video) : Effect
        data class Pause(val video: Media.Video) : Effect
        data class ReleaseVideoPlayer(val video: Media.Video) : Effect
    }

    private var videoPlaying: Media.Video? = null

    private val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private suspend fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        _effect.send(effectValue)
    }

    private fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    suspend fun init() {
        setState { copy(loading = true) }
        println("MediaRepository loading")
        val medias = getMedias()
        println("MediaRepository not loading")
        setState { copy(medias = medias, loading = false) }
    }

    suspend fun onDispose(video: Media.Video) {
        videoPlaying = null
        setEffect { Effect.ReleaseVideoPlayer(video) }
    }

    private suspend fun play(video: Media.Video) {
        setEffect { Effect.Play(video) }
        videoPlaying = video
    }

    private suspend fun pause(video: Media.Video) {
        setEffect { Effect.Pause(video) }
        videoPlaying = null
    }

    suspend fun onClickVideo(video: Media.Video) {
        if (videoPlaying != null && video != videoPlaying) {
            pause(requireNotNull(videoPlaying))
        }

        if (videoPlaying == null) {
            play(video)
        } else {
            pause(video)
        }
    }
}