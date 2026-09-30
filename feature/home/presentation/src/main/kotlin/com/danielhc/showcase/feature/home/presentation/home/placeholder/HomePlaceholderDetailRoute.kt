package com.danielhc.showcase.feature.home.presentation.home.placeholder

import kotlinx.serialization.Serializable

/**
 * Carries [itemTitle] directly rather than looking it up from a repository — there is no data
 * layer for this placeholder by design (no feature exists to show yet). Replaced entirely once
 * a real feature lands, at which point this becomes ordinary cross-feature navigation.
 */
@Serializable
data class HomePlaceholderDetailRoute(val itemId: String, val itemTitle: String)
