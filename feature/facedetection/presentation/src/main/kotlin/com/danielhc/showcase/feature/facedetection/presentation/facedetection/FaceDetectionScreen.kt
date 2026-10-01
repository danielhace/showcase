package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielhc.showcase.core.designsystem.component.rippleClickable
import com.danielhc.showcase.core.designsystem.theme.DanielShowcaseTheme
import com.danielhc.showcase.core.presentation.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun FaceDetectionRoot(
    onNavigateBack: () -> Unit,
    viewModel: FaceDetectionViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // Not delegated: reading .value only inside the overlay's draw lambda means a new detection
    // frame redraws that Canvas without recomposing this Root or the screen.
    val faces = viewModel.faces.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = LocalActivity.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            FaceDetectionEvent.NavigateBack -> onNavigateBack()
        }
    }

    // Saved so they survive rotation and process death alongside the system's own grant state.
    var permissionRequested by rememberSaveable { mutableStateOf(false) }
    var deniedBefore by rememberSaveable { mutableStateOf(false) }
    var rationaleBeforeRequest by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        val rationaleAfter = activity?.shouldShowCameraRationale() ?: true
        // The system exposes no "don't ask again" flag. Dismissing the dialog also returns a
        // denial with no rationale, so only call it permanent when the rationale was showable
        // before this request and isn't now, or when it was never showable across two denials.
        val canAskAgain = rationaleAfter || (!rationaleBeforeRequest && !deniedBefore)
        if (!granted) deniedBefore = true
        viewModel.onAction(FaceDetectionAction.OnPermissionResult(granted, canAskAgain))
    }
    val requestPermission = {
        permissionRequested = true
        rationaleBeforeRequest = activity?.shouldShowCameraRationale() ?: false
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // Ask once on first entry; afterwards the user drives retries.
    LaunchedEffect(Unit) {
        if (!permissionRequested && !context.hasCameraPermission()) requestPermission()
    }

    // Picks up a grant made from app settings (or already present) whenever the screen resumes.
    // Also restores the denied state after process death, when the ViewModel starts over at
    // Unknown but the saved flag means the prompt won't be shown again automatically.
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        when {
            context.hasCameraPermission() -> viewModel.onAction(
                FaceDetectionAction.OnPermissionResult(granted = true, canAskAgain = true),
            )
            permissionRequested && state.cameraPermission == CameraPermission.Unknown -> viewModel.onAction(
                FaceDetectionAction.OnPermissionResult(
                    granted = false,
                    canAskAgain = (activity?.shouldShowCameraRationale() ?: true) || !deniedBefore,
                ),
            )
        }
    }

    FaceDetectionScreen(
        state = state,
        faces = { faces.value },
        onAction = viewModel::onAction,
        onRequestPermission = {
            if (state.cameraPermission == CameraPermission.PermanentlyDenied) {
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null),
                    ),
                )
            } else {
                requestPermission()
            }
        },
        cameraContent = { modifier ->
            CameraFacePreview(
                lens = state.lens,
                onFacesDetected = { viewModel.onAction(FaceDetectionAction.OnFacesDetected(it)) },
                modifier = modifier,
            )
        },
    )
}

/**
 * [cameraContent] is a slot so previews and tests can render the overlay and chrome without a
 * real camera.
 */
@Composable
fun FaceDetectionScreen(
    state: FaceDetectionState,
    faces: () -> List<FaceBoxUi>,
    onAction: (FaceDetectionAction) -> Unit,
    onRequestPermission: () -> Unit,
    cameraContent: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        when (state.cameraPermission) {
            CameraPermission.Granted -> {
                cameraContent(Modifier.fillMaxSize())
                FaceOverlay(faces = faces, modifier = Modifier.fillMaxSize())
                CameraButton(
                    icon = FaceDetectionIcons.FlipCamera,
                    contentDescription = "Switch camera",
                    onClick = { onAction(FaceDetectionAction.OnFlipCameraClick) },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(bottom = 32.dp),
                )
            }
            CameraPermission.Denied, CameraPermission.PermanentlyDenied -> PermissionMessage(
                permanentlyDenied = state.cameraPermission == CameraPermission.PermanentlyDenied,
                onRequestPermission = onRequestPermission,
                modifier = Modifier.align(Alignment.Center),
            )
            CameraPermission.Unknown -> Unit
        }

        CameraButton(
            icon = FaceDetectionIcons.Back,
            contentDescription = "Back",
            onClick = { onAction(FaceDetectionAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp),
        )
    }
}

/**
 * Invokes [faces] only inside the draw lambda, so a new detection result redraws this Canvas
 * without recomposing or relaying out anything else.
 */
@Composable
private fun FaceOverlay(
    faces: () -> List<FaceBoxUi>,
    modifier: Modifier = Modifier,
) {
    val color = MaterialTheme.colorScheme.secondary
    Canvas(modifier = modifier) {
        val stroke = Stroke(width = 2.dp.toPx())
        faces().forEach { face ->
            drawRect(
                color = color,
                topLeft = Offset(face.left, face.top),
                size = Size(face.size, face.size),
                style = stroke,
            )
        }
    }
}

@Composable
private fun CameraButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = Color.Black.copy(alpha = 0.45f),
            contentColor = Color.White,
        ),
        modifier = modifier,
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Composable
private fun PermissionMessage(
    permanentlyDenied: Boolean,
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Camera access is needed to detect faces.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
        Text(
            text = if (permanentlyDenied) "Open settings" else "Allow camera",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(top = 20.dp)
                .rippleClickable(onRequestPermission)
                .padding(horizontal = 12.dp, vertical = 8.dp),
        )
    }
}

private fun Activity.shouldShowCameraRationale(): Boolean =
    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

private fun Context.hasCameraPermission(): Boolean =
    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
        PackageManager.PERMISSION_GRANTED

@Preview
@Composable
private fun FaceDetectionScreenPreview() {
    DanielShowcaseTheme {
        FaceDetectionScreen(
            state = FaceDetectionState(cameraPermission = CameraPermission.Granted),
            faces = { listOf(FaceBoxUi(trackingId = 1, left = 220f, top = 480f, size = 520f)) },
            onAction = {},
            onRequestPermission = {},
            cameraContent = { Box(it.background(Color.DarkGray)) },
        )
    }
}

@Preview
@Composable
private fun FaceDetectionPermissionDeniedPreview() {
    DanielShowcaseTheme {
        FaceDetectionScreen(
            state = FaceDetectionState(cameraPermission = CameraPermission.Denied),
            faces = { emptyList() },
            onAction = {},
            onRequestPermission = {},
            cameraContent = {},
        )
    }
}
