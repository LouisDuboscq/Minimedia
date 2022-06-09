package com.lduboscq.minimedia.presentation

import com.lduboscq.minimedia.domain.GetDetailMedia
import com.lduboscq.minimedia.domain.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(
    private val getDetailMedia: GetDetailMedia
) {

    data class State(
        val loading: Boolean = false,
        val story: Media.Story? = null
    )

    private val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    private fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    suspend fun init(id: Long) {
        setState { copy(loading = true) }
        val story = getDetailMedia(id) as? Media.Story
        setState { copy(story = story, loading = false) }
    }
}
