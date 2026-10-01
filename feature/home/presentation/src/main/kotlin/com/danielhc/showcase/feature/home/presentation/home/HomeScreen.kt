package com.danielhc.showcase.feature.home.presentation.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielhc.showcase.core.designsystem.component.rippleClickable
import com.danielhc.showcase.core.designsystem.theme.DanielShowcaseTheme
import com.danielhc.showcase.core.presentation.ObserveAsEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    onNavigateToFaceDetection: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            HomeEvent.NavigateToFaceDetection -> onNavigateToFaceDetection()
        }
    }

    HomeScreen(state = state, onAction = viewModel::onAction)
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Plays the row entrance stagger exactly once per screen instance — survives recomposition
    // and scrolling (an item leaving/re-entering the lazy viewport won't replay it), and survives
    // rotation via rememberSaveable.
    var entranceAnimationConsumed by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) { entranceAnimationConsumed = true }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = "Showcase",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = 12.dp),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = WindowInsets.navigationBars
                .only(WindowInsetsSides.Bottom)
                .add(WindowInsets(bottom = 24.dp))
                .asPaddingValues(),
        ) {
            itemsIndexed(state.items, key = { _, item -> item.id }) { index, item ->
                val playEntrance = remember { !entranceAnimationConsumed }
                HomeRow(
                    item = item,
                    index = index,
                    playEntrance = playEntrance,
                    onClick = { onAction(HomeAction.OnItemClick(item.id)) },
                )
                if (index != state.items.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeRow(
    item: HomeItemUi,
    index: Int,
    playEntrance: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val alpha = remember { Animatable(if (playEntrance) 0f else 1f) }
    val translationY = remember { Animatable(if (playEntrance) 24f else 0f) }

    LaunchedEffect(Unit) {
        if (playEntrance) {
            delay(index * 35L)
            launch { alpha.animateTo(1f, tween(280)) }
            launch { translationY.animateTo(0f, tween(280, easing = FastOutSlowInEasing)) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.alpha = alpha.value
                this.translationY = translationY.value
            }
            .rippleClickable(onClick)
            .padding(horizontal = 24.dp, vertical = 18.dp),
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DanielShowcaseTheme {
        HomeScreen(
            state = HomeState(
                items = listOf(
                    HomeItemUi("face-detection", "Live face detection", "CameraX and ML Kit tracking faces in real time."),
                ),
            ),
            onAction = {},
        )
    }
}
