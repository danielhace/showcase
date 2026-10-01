package com.danielhc.showcase.feature.home.presentation.home

sealed interface HomeEvent {
    data object NavigateToFaceDetection : HomeEvent
}
