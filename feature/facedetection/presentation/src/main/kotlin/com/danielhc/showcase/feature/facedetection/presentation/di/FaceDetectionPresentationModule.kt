package com.danielhc.showcase.feature.facedetection.presentation.di

import com.danielhc.showcase.feature.facedetection.presentation.facedetection.FaceDetectionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val faceDetectionPresentationModule = module {
    viewModelOf(::FaceDetectionViewModel)
}
