package com.piappstudio.digitaldiary.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.piappstudio.digitaldiary.common.theme.Dimens

@Composable
fun PiHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBackClick: (() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(top = statusBarPadding.calculateTopPadding())
            .padding(horizontal = Dimens.card_padding_lg, vertical = Dimens.space)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(Dimens.icon_size_lg)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor,
                        modifier = Modifier.size(Dimens.icon_size_md)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (onBackClick != null) Dimens.space else 0.dp),
                horizontalAlignment = if (onBackClick != null && subtitle == null) Alignment.CenterHorizontally else Alignment.Start
            ) {
                Text(
                    title,
                    style = if (subtitle != null) MaterialTheme.typography.headlineLarge else MaterialTheme.typography.titleMedium,
                    color = contentColor,
                    textAlign = if (onBackClick != null && subtitle == null) TextAlign.Center else TextAlign.Start,
                    modifier = if (onBackClick != null && subtitle == null) Modifier.fillMaxWidth() else Modifier
                )
                if (subtitle != null) {
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Start
                    )
                }
            }

            Row(
                modifier = Modifier.widthIn(min = if (onBackClick == null && subtitle == null) 0.dp else Dimens.icon_size_lg),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions()
            }
        }
    }
}

@Composable
fun RowScope.PiActionIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null,
    isLoading: Boolean = false,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    if (isLoading) {
        Box(
            modifier = Modifier.size(Dimens.icon_size_lg),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimens.icon_size_sm),
                strokeWidth = 2.dp,
                color = tint
            )
        }
    } else {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(Dimens.icon_size_lg)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(Dimens.icon_size_md)
            )
        }
    }
}
