package com.danielhc.showcase.feature.home.presentation.di

import com.danielhc.showcase.feature.home.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
}
