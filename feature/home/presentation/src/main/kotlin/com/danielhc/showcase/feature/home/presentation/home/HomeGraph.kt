package com.danielhc.showcase.feature.home.presentation.home

import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * Each demo lives in its own feature module; Home only knows about them through these
 * callbacks, which `:app` wires to the matching routes.
 */
fun NavGraphBuilder.homeGraph(onNavigateToFaceDetection: () -> Unit) {
    composable<HomeRoute> {
        // A double tap would otherwise push the demo twice.
        HomeRoot(onNavigateToFaceDetection = dropUnlessResumed(block = onNavigateToFaceDetection))
    }
}
