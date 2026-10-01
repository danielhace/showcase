package com.danielhc.showcase.feature.facedetection.presentation.facedetection

import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.faceDetectionGraph(onNavigateBack: () -> Unit) {
    composable<FaceDetectionRoute> {
        // Drops repeat back events once this entry has left RESUMED, so a double tap can't pop
        // past Home and empty the back stack.
        FaceDetectionRoot(onNavigateBack = dropUnlessResumed(block = onNavigateBack))
    }
}
