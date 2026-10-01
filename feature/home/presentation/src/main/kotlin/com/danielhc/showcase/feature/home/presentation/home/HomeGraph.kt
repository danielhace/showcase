package com.danielhc.showcase.feature.home.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.danielhc.showcase.feature.home.presentation.home.placeholder.HomePlaceholderDetailRoute
import com.danielhc.showcase.feature.home.presentation.home.placeholder.HomePlaceholderDetailScreen

/**
 * Both the list and its placeholder detail live inside this one feature graph for now — there
 * is no other feature to navigate into yet. Once real features exist, the placeholder route is
 * replaced by ordinary cross-feature navigation expressed as a callback.
 */
fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable<HomeRoute> {
        HomeRoot(
            onNavigateToPlaceholder = { id, title ->
                navController.navigate(HomePlaceholderDetailRoute(id, title))
            },
        )
    }
    composable<HomePlaceholderDetailRoute> { backStackEntry ->
        val route: HomePlaceholderDetailRoute = backStackEntry.toRoute()
        HomePlaceholderDetailScreen(
            itemTitle = route.itemTitle,
            onBackClick = navController::popBackStack,
        )
    }
}
