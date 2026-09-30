package com.danielhc.showcase.feature.home.presentation.home

sealed interface HomeAction {
    data class OnItemClick(val itemId: String) : HomeAction
}
