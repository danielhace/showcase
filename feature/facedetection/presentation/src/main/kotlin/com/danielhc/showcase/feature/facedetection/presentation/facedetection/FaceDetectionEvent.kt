package com.danielhc.showcase.feature.facedetection.presentation.facedetection

sealed interface FaceDetectionEvent {
    data object NavigateBack : FaceDetectionEvent
}
