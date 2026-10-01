package com.danielhc.showcase.feature.home.presentation.home

import androidx.compose.runtime.Stable

@Stable
data class HomeState(
    val items: List<HomeItemUi> = emptyList(),
)
