package com.danielhc.showcase.feature.home.presentation.home

import app.cash.turbine.test
import assertk.assertThat
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
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `face detection is the first item`() {
        val viewModel = HomeViewModel()

        assertThat(viewModel.state.value.items.first().id).isEqualTo(FACE_DETECTION_ID)
    }

    @Test
    fun `clicking face detection sends NavigateToFaceDetection`() = runTest {
        val viewModel = HomeViewModel()

        viewModel.events.test {
            viewModel.onAction(HomeAction.OnItemClick(FACE_DETECTION_ID))
            assertThat(awaitItem()).isEqualTo(HomeEvent.NavigateToFaceDetection)
        }
    }

    @Test
    fun `clicking an unknown item sends nothing`() = runTest {
        val viewModel = HomeViewModel()

        viewModel.events.test {
            viewModel.onAction(HomeAction.OnItemClick("unknown"))
            expectNoEvents()
        }
    }
}
