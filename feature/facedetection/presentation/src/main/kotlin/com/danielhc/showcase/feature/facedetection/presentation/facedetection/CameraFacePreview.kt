package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import android.graphics.Rect
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

/**
 * Live camera preview that runs ML Kit face detection on every analysis frame.
 *
 * Uses [MlKitAnalyzer] with [ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED], so face bounds
 * arrive already mapped into [PreviewView] pixels — rotation, center-crop scaling and front-lens
 * mirroring are handled by CameraX rather than by hand-written matrix math.
 *
 * Must only be composed once the CAMERA permission has been granted.
 */
@Composable
internal fun CameraFacePreview(
    lens: CameraLens,
    onFacesDetected: (List<FaceBoxUi>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnFacesDetected by rememberUpdatedState(onFacesDetected)

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            // Set before the first bind so the back camera never opens briefly on entry.
            cameraSelector = lens.toSelector()
        }
    }

    DisposableEffect(controller, lifecycleOwner) {
        val mainExecutor = ContextCompat.getMainExecutor(context)
        val detector = FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .enableTracking()
                .build(),
        )
        controller.setImageAnalysisAnalyzer(
            mainExecutor,
            MlKitAnalyzer(
                listOf(detector),
                ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED,
                mainExecutor,
            ) { result ->
                // A failed frame is skipped rather than clearing the boxes, avoiding flicker.
                val faces = result.getValue(detector) ?: return@MlKitAnalyzer
                currentOnFacesDetected(faces.map(Face::toFaceBox))
            },
        )
        controller.bindToLifecycle(lifecycleOwner)

        onDispose {
            controller.clearImageAnalysisAnalyzer()
            controller.unbind()
            detector.close()
        }
    }

    LaunchedEffect(controller, lens) {
        // Throws if this device lacks the requested lens; keep the current camera in that case.
        runCatching { controller.cameraSelector = lens.toSelector() }
    }

    AndroidView(
        factory = { viewContext ->
            PreviewView(viewContext).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                this.controller = controller
            }
        },
        modifier = modifier,
    )
}

private fun CameraLens.toSelector(): CameraSelector = when (this) {
    CameraLens.Front -> CameraSelector.DEFAULT_FRONT_CAMERA
    CameraLens.Back -> CameraSelector.DEFAULT_BACK_CAMERA
}

private fun Face.toFaceBox(): FaceBoxUi = boundingBox.toSquare(trackingId)

private fun Rect.toSquare(trackingId: Int?): FaceBoxUi {
    val side = maxOf(width(), height()).toFloat()
    return FaceBoxUi(
        trackingId = trackingId,
        left = exactCenterX() - side / 2f,
        top = exactCenterY() - side / 2f,
        size = side,
    )
}
