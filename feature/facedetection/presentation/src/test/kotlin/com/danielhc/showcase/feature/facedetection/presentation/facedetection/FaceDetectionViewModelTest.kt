package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FaceDetectionViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val face = FaceBoxUi(trackingId = 1, left = 10f, top = 20f, size = 100f)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is front lens with unknown permission and no faces`() {
        val state = FaceDetectionViewModel().state.value

        assertThat(state).isEqualTo(FaceDetectionState())
        assertThat(state.lens).isEqualTo(CameraLens.Front)
        assertThat(state.cameraPermission).isEqualTo(CameraPermission.Unknown)
        assertThat(FaceDetectionViewModel().faces.value).isEmpty()
    }

    @Test
    fun `permission result maps to granted, denied and permanently denied`() {
        val viewModel = FaceDetectionViewModel()

        viewModel.onAction(FaceDetectionAction.OnPermissionResult(granted = false, canAskAgain = true))
        assertThat(viewModel.state.value.cameraPermission).isEqualTo(CameraPermission.Denied)

        viewModel.onAction(FaceDetectionAction.OnPermissionResult(granted = false, canAskAgain = false))
        assertThat(viewModel.state.value.cameraPermission).isEqualTo(CameraPermission.PermanentlyDenied)

        viewModel.onAction(FaceDetectionAction.OnPermissionResult(granted = true, canAskAgain = false))
        assertThat(viewModel.state.value.cameraPermission).isEqualTo(CameraPermission.Granted)
    }

    @Test
    fun `detected faces are exposed on the faces flow`() {
        val viewModel = FaceDetectionViewModel()

        viewModel.onAction(FaceDetectionAction.OnFacesDetected(listOf(face)))

        assertThat(viewModel.faces.value).isEqualTo(listOf(face))
    }

    @Test
    fun `flipping toggles the lens and clears stale faces`() {
        val viewModel = FaceDetectionViewModel()
        viewModel.onAction(FaceDetectionAction.OnFacesDetected(listOf(face)))

        viewModel.onAction(FaceDetectionAction.OnFlipCameraClick)
        assertThat(viewModel.state.value.lens).isEqualTo(CameraLens.Back)
        assertThat(viewModel.faces.value).isEmpty()

        viewModel.onAction(FaceDetectionAction.OnFlipCameraClick)
        assertThat(viewModel.state.value.lens).isEqualTo(CameraLens.Front)
    }

    @Test
    fun `back click sends NavigateBack`() = runTest {
        val viewModel = FaceDetectionViewModel()

        viewModel.events.test {
            viewModel.onAction(FaceDetectionAction.OnBackClick)
            assertThat(awaitItem()).isEqualTo(FaceDetectionEvent.NavigateBack)
        }
    }
}
