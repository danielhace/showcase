package com.danielhc.showcase.feature.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    init {
        _state.update { it.copy(items = homeItems) }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnItemClick -> onItemClick(action.itemId)
        }
    }

    private fun onItemClick(itemId: String) {
        val event = when (itemId) {
            FACE_DETECTION_ID -> HomeEvent.NavigateToFaceDetection
            else -> return
        }
        viewModelScope.launch { _events.send(event) }
    }
}

internal const val FACE_DETECTION_ID = "face-detection"

private val homeItems = listOf(
    HomeItemUi(
        id = FACE_DETECTION_ID,
        title = "Live face detection",
        description = "CameraX and ML Kit tracking faces in real time.",
    ),
)
