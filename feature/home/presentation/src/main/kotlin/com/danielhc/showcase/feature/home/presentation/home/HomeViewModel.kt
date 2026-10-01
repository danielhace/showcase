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
        _state.update { it.copy(items = dummyHomeItems) }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnItemClick -> onItemClick(action.itemId)
        }
    }

    private fun onItemClick(itemId: String) {
        val item = _state.value.items.firstOrNull { it.id == itemId } ?: return
        viewModelScope.launch {
            _events.send(HomeEvent.NavigateToDetail(item.id, item.title))
        }
    }
}

private val dummyHomeItems = listOf(
    HomeItemUi(
        id = "motion-choreography",
        title = "Motion choreography",
        description = "Orchestrated, physics-based transitions across a full screen.",
    ),
    HomeItemUi(
        id = "gesture-driven-nav",
        title = "Gesture-driven navigation",
        description = "Predictive back and drag-to-dismiss on the platform gesture APIs.",
    ),
    HomeItemUi(
        id = "shared-element-transitions",
        title = "Shared element transitions",
        description = "Seamless element continuity carried between two distinct screens.",
    ),
    HomeItemUi(
        id = "custom-shaders",
        title = "Custom shaders",
        description = "AGSL-backed visual effects rendered directly on the GPU.",
    ),
    HomeItemUi(
        id = "adaptive-layouts",
        title = "Adaptive layouts",
        description = "One codebase reflowing across phone, foldable, and tablet.",
    ),
    HomeItemUi(
        id = "haptic-feedback",
        title = "Haptic feedback design",
        description = "Precise, composable haptic patterns tied to on-screen motion.",
    ),
    HomeItemUi(
        id = "home-widgets",
        title = "Home screen widgets",
        description = "Interactive Glance widgets sharing state with the app.",
    ),
    HomeItemUi(
        id = "live-data-viz",
        title = "Live data visualization",
        description = "Real-time charts driven by streaming data and Canvas.",
    ),
    HomeItemUi(
        id = "camera-effects",
        title = "Camera effects pipeline",
        description = "CameraX with a custom real-time image-processing pipeline.",
    ),
    HomeItemUi(
        id = "offline-first-sync",
        title = "Offline-first sync",
        description = "Local-first data with background conflict resolution.",
    ),
    HomeItemUi(
        id = "accessibility-depth",
        title = "Accessibility depth",
        description = "TalkBack, switch access, and large-text support done right.",
    ),
    HomeItemUi(
        id = "wear-companion",
        title = "Wear OS companion",
        description = "A companion tile and complication sharing logic with the phone app.",
    ),
    HomeItemUi(
        id = "biometric-auth",
        title = "Biometric authentication",
        description = "BiometricPrompt integrated into a secure, resumable flow.",
    ),
    HomeItemUi(
        id = "background-work",
        title = "Background work orchestration",
        description = "WorkManager chains for long-running, constrained tasks.",
    ),
    HomeItemUi(
        id = "performance-profiling",
        title = "Performance profiling",
        description = "A built-in frame-timing overlay for diagnosing jank live.",
    ),
)
