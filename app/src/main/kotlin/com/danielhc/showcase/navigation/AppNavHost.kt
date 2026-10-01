package com.danielhc.showcase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.danielhc.showcase.feature.facedetection.presentation.facedetection.FaceDetectionRoute
import com.danielhc.showcase.feature.facedetection.presentation.facedetection.faceDetectionGraph
import com.danielhc.showcase.feature.home.presentation.home.HomeRoute
import com.danielhc.showcase.feature.home.presentation.home.homeGraph

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeRoute) {
        homeGraph(onNavigateToFaceDetection = { navController.navigate(FaceDetectionRoute) { launchSingleTop = true } })
        faceDetectionGraph(onNavigateBack = navController::popBackStack)
    }
}
