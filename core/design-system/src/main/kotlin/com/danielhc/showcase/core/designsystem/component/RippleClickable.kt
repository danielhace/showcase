package com.danielhc.showcase.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/** The app's standard tap treatment: a ripple tinted with the theme's clay accent. */
fun Modifier.rippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(color = MaterialTheme.colorScheme.secondary),
        onClick = onClick,
    )
}
