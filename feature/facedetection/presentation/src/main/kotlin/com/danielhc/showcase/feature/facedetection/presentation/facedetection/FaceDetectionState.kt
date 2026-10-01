package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import androidx.compose.runtime.Stable

@Stable
data class FaceDetectionState(
    val cameraPermission: CameraPermission = CameraPermission.Unknown,
    val lens: CameraLens = CameraLens.Front,
)

enum class CameraPermission {
    /** Not resolved yet — the system prompt hasn't returned. */
    Unknown,
    Granted,

    /** Denied, but the system prompt can still be shown again. */
    Denied,

    /** Denied with "don't ask again" — only app settings can grant it now. */
    PermanentlyDenied,
}

enum class CameraLens { Front, Back }
