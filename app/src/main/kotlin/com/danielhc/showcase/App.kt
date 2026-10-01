package com.danielhc.showcase

import android.app.Application
import com.danielhc.showcase.feature.home.presentation.di.homePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(homePresentationModule)
        }
    }
}
