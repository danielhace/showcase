package com.danielhc.showcase.feature.home.presentation.home

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
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
    fun `initial state has exactly 15 items`() {
        val viewModel = HomeViewModel()

        assertThat(viewModel.state.value.items).hasSize(15)
    }

    @Test
    fun `clicking an item sends NavigateToDetail with matching id and title`() = runTest {
        val viewModel = HomeViewModel()
        val item = viewModel.state.value.items.first()

        viewModel.events.test {
            viewModel.onAction(HomeAction.OnItemClick(item.id))
            assertThat(awaitItem()).isEqualTo(HomeEvent.NavigateToDetail(item.id, item.title))
        }
    }
}
