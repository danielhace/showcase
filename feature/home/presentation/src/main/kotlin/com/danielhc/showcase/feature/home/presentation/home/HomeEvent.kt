package com.danielhc.showcase.feature.home.presentation.home

sealed interface HomeEvent {
    data class NavigateToDetail(val itemId: String, val itemTitle: String) : HomeEvent
}
