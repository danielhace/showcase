package com.danielhc.showcase.feature.home.presentation.home.placeholder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielhc.showcase.core.designsystem.component.rippleClickable
import com.danielhc.showcase.core.designsystem.theme.DanielShowcaseTheme

/**
 * A plain stand-in with no State/Action/Event/ViewModel — there is nothing to manage yet.
 * Gets replaced entirely once a real feature exists behind this item.
 */
@Composable
fun HomePlaceholderDetailScreen(
    itemTitle: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = itemTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "This demo isn't built yet — coming soon.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = "Back",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 32.dp)
                .wrapContentSize(Alignment.CenterStart)
                .rippleClickable(onBackClick)
                .padding(vertical = 8.dp),
        )
    }
}

@Preview
@Composable
private fun HomePlaceholderDetailScreenPreview() {
    DanielShowcaseTheme {
        HomePlaceholderDetailScreen(itemTitle = "Motion choreography", onBackClick = {})
    }
}
