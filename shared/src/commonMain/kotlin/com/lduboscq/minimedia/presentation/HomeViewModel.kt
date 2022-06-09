package com.lduboscq.minimedia.presentation

import com.lduboscq.minimedia.domain.GetMedias
import com.lduboscq.minimedia.domain.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val getMedias: GetMedias
) {

    data class State(
        val loading: Boolean = false,
        val medias: List<Media> = emptyList()
    )

    private val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

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
}