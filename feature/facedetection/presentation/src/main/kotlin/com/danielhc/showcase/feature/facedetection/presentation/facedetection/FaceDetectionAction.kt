package com.danielhc.showcase.feature.facedetection.presentation.facedetection

sealed interface FaceDetectionAction {
    data class OnPermissionResult(val granted: Boolean, val canAskAgain: Boolean) : FaceDetectionAction
    data class OnFacesDetected(val faces: List<FaceBoxUi>) : FaceDetectionAction
    data object OnFlipCameraClick : FaceDetectionAction
    data object OnBackClick : FaceDetectionAction
}
