package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FaceDetectionViewModel : ViewModel() {

    private val _state = MutableStateFlow(FaceDetectionState())
    val state = _state.asStateFlow()

    /**
     * Kept out of [state] because it changes on every analysis frame (~30/s); a separate flow lets
     * the UI read it in the draw phase only, instead of recomposing the whole screen per frame.
     */
    private val _faces = MutableStateFlow<List<FaceBoxUi>>(emptyList())
    val faces = _faces.asStateFlow()

    private val _events = Channel<FaceDetectionEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: FaceDetectionAction) {
        when (action) {
            is FaceDetectionAction.OnPermissionResult -> onPermissionResult(action.granted, action.canAskAgain)
            is FaceDetectionAction.OnFacesDetected -> _faces.value = action.faces
            FaceDetectionAction.OnFlipCameraClick -> onFlipCamera()
            FaceDetectionAction.OnBackClick -> viewModelScope.launch {
                _events.send(FaceDetectionEvent.NavigateBack)
            }
        }
    }

    private fun onPermissionResult(granted: Boolean, canAskAgain: Boolean) {
        val permission = when {
            granted -> CameraPermission.Granted
            canAskAgain -> CameraPermission.Denied
            else -> CameraPermission.PermanentlyDenied
        }
        _state.update { it.copy(cameraPermission = permission) }
    }

    private fun onFlipCamera() {
        _state.update {
            it.copy(lens = if (it.lens == CameraLens.Front) CameraLens.Back else CameraLens.Front)
        }
        // Boxes from the previous lens are in a different (mirrored) frame — drop them.
        _faces.value = emptyList()
    }
}
