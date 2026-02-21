package com.piappstudio.digitaldiary.ui.diary.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import com.piappstudio.digitaldiary.ui.component.PiActionIcon
import com.piappstudio.digitaldiary.ui.component.PiHeader
import com.piappstudio.digitaldiary.ui.diary.component.PiConfirmationAlertDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiaryDetailScreen(
    eventId: Long,
    onBackClick: () -> Unit,
    onNavigateEdit: (Long) -> Unit,
    viewModel: DiaryDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val userEvent = uiState.userEvent
    val medias = uiState.medias
    val isLoading = uiState.isLoading
    val error= uiState.error

    LaunchedEffect(eventId) {
        viewModel.loadData(eventId)
    }

    if (isLoading) {
        LoadingDetailScreen()
    } else if (error != null) {
        ErrorDetailScreen(
            errorMessage = error ?: "Unknown error",
            onRetry = { viewModel.loadData(eventId) },
            onBack = onBackClick
        )
    } else if (userEvent != null) {
        DetailContent(
            userEvent = userEvent,
            medias = medias,
            onBackClick = onBackClick,
            onEdit = { onNavigateEdit(eventId) },
            onDelete = {
                viewModel.deleteEvent(eventId) {
                    onBackClick()
                }
            }
        )
    } else {
        ErrorDetailScreen(
            errorMessage = "No diary entry found",
            onRetry = { viewModel.loadData(eventId) },
            onBack = onBackClick
        )
    }
}

@Composable
private fun DetailContent(
    userEvent: UserEvent,
    medias: List<MediaInfo>,
    onBackClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val emotionIndex = userEvent.eventInfo.emotion.hashCode().rem(6).coerceAtLeast(0)
    val emotionColor = getTemplateColor(emotionIndex)
    var showDeleteDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with back button
        item {
            PiHeader(
                title = "📖 Diary Entry",
                onBackClick = onBackClick,
                actions = {
                    PiActionIcon(
                        icon = Icons.Default.Edit,
                        onClick = onEdit,
                        contentDescription = "Edit"
                    )
                    PiActionIcon(
                        icon = Icons.Default.Delete,
                        onClick = { showDeleteDialog = true },
                        contentDescription = "Delete"
                    )
                }
            )
        }

        // Title
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.card_padding_lg)
            ) {
                Text(
                    userEvent.eventInfo.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = Dimens.space)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            emotionColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(Dimens.corner_sm)
                        )
                        .padding(Dimens.space),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date",
                        tint = emotionColor,
                        modifier = Modifier.size(Dimens.icon_size_sm)
                    )
                    Text(
                        "  ${userEvent.eventInfo.dateInfo}",
                        style = MaterialTheme.typography.labelMedium,
                        color = emotionColor,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = Dimens.half_space)
                    )
                    Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(Dimens.corner_full),
                        color = emotionColor.copy(alpha = 0.2f)
                    ) {
                        Text(
                            userEvent.eventInfo.emotion,
                            style = MaterialTheme.typography.labelSmall,
                            color = emotionColor,
                            modifier = Modifier.padding(
                                horizontal = Dimens.space,
                                vertical = Dimens.half_space
                            )
                        )
                    }
                }
            }
        }

        // Description
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.card_padding_lg)
            ) {
                Text(
                    "Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = Dimens.space)
                )
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.corner_lg),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Text(
                        userEvent.eventInfo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(Dimens.card_padding_md),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }

        // Tags section
        if (!userEvent.tags.isNullOrEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.card_padding_lg)
                ) {
                    Text(
                        "Tags",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = Dimens.space)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.half_space),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        userEvent.tags!!.forEach { tag ->
                            Surface(
                                modifier = Modifier,
                                shape = RoundedCornerShape(Dimens.corner_full),
                                color = emotionColor.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = Dimens.space,
                                        vertical = Dimens.half_space
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Label,
                                        contentDescription = "Tag",
                                        tint = emotionColor,
                                        modifier = Modifier.size(Dimens.icon_size_xs)
                                    )
                                    Text(
                                        " ${tag.tagName}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = emotionColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Photos section
        if (medias.isNotEmpty()) {
            item {
                Text(
                    "Photos (${medias.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(
                        start = Dimens.card_padding_lg,
                        top = Dimens.card_padding_lg,
                        bottom = Dimens.space
                    )
                )
            }

            items(medias) { media ->
                PhotoItem(media = media)
            }
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(Dimens.biggest_space))
        }
    }

    if (showDeleteDialog) {
        PiConfirmationAlertDialog(title = "Delete Entry", message = "Are you sure you want to delete this diary entry?", onDismiss = { showDeleteDialog = false }, onConfirm = {
            onDelete()
            showDeleteDialog = false
        })
    }
}

@Composable
private fun PhotoItem(media: MediaInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.card_padding_lg,
                vertical = Dimens.space
            ),
        shape = RoundedCornerShape(Dimens.corner_lg),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.elevation_4)
    ) {
        AsyncImage(
            model = media.mediaPath,
            contentDescription = "Diary Photo",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun LoadingDetailScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Dimens.card_padding_lg)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(Dimens.icon_size_xl)
                    .padding(bottom = Dimens.big_space)
            )
            Text(
                "Loading diary entry...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun ErrorDetailScreen(
    errorMessage: String,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.card_padding_lg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(Dimens.corner_lg)
                )
                .padding(Dimens.card_padding_lg)
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .size(Dimens.space_48)
                    .padding(bottom = Dimens.big_space)
            )
            Text(
                "Oops!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = Dimens.big_space)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.space),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.button_height_md),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text("Go Back")
                }
                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.button_height_md),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Retry")
                }
            }
        }
    }
}