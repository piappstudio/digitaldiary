package com.piappstudio.digitaldiary.ui.diary

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piappstudio.digitaldiary.common.theme.DiaryMood
import com.piappstudio.digitaldiary.common.theme.DigitalDiaryTheme
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.TagInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiaryDashboardScreen(
    onNavigateDetail: (Long) -> Unit,
    viewModel: DiaryDashboardViewModel = koinViewModel()
) {
    val userEvents by viewModel.userEvents.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header Section with Title and Refresh Button
        DiaryDashboardHeader(
            onRefreshClick = { viewModel.refreshEvents() }
        )

        // Content Section - Events List or Empty State
        if (isLoading && userEvents.isEmpty()) {
            LoadingScreen()
        } else if (userEvents.isEmpty()) {
            EmptyEventsScreen()
        } else {
            EventsList(
                events = userEvents,
                onNavigateEdit = onNavigateDetail,
                onDelete = { viewModel.deleteEvent(it) }
            )
        }
    }
}

@Composable
private fun DiaryDashboardHeader(
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary
            )
            .padding(Dimens.card_padding_lg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "My Diary",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = Dimens.half_space)
                )
                Text(
                    "Your daily moments and thoughts",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }

            IconButton(
                onClick = onRefreshClick,
                modifier = Modifier.size(Dimens.icon_size_lg)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh events",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(Dimens.icon_size_md)
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Dimens.icon_size_xl)
        )
    }
}

@Composable
private fun EmptyEventsScreen() {
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
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(Dimens.corner_2xl)
                    )
                    .padding(Dimens.card_padding_lg)
            ) {
            Text(
                "📝",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = Dimens.big_space)
            )
            Text(
                "No entries yet",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Start writing your first diary entry to see it here!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = Dimens.big_space)
            )
        }
    }
}

@Composable
private fun EventsList(
    events: List<UserEvent>,
    onNavigateEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = Dimens.card_padding_lg,
                vertical = Dimens.card_padding_md
            ),
        verticalArrangement = Arrangement.spacedBy(Dimens.bigger_space)
    ) {
        items(events) { userEvent ->
            EventCard(
                userEvent = userEvent,
                onClick = { onNavigateEdit(userEvent.eventInfo.eventId ?: 0L) },
                onDelete = { onDelete(userEvent.eventInfo.eventId ?: 0L) },
                onShare = { /* Handle share */ }
            )
        }
        item {
            Spacer(modifier = Modifier.height(Dimens.biggest_space))
        }
    }
}

@Composable
private fun EventCard(
    userEvent: UserEvent,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    // Get emotion index for color assignment
    val emotionIndex = userEvent.eventInfo.emotion.hashCode().rem(6).coerceAtLeast(0)
    val emotionColor = getTemplateColor(emotionIndex)

    val cardShape = RoundedCornerShape(Dimens.corner_2xl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(cardShape)
            .shadow(
                elevation = 8.dp,
                shape = cardShape,
                clip = false
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.card_padding_lg)
        ) {
            // Top section with color indicator and date
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.big_space),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emotion color indicator with larger corners
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(emotionColor, shape = RoundedCornerShape(Dimens.corner_sm))
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        userEvent.eventInfo.dateInfo,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = Dimens.space)
                    )
                    Text(
                        userEvent.eventInfo.emotion,
                        style = MaterialTheme.typography.labelSmall,
                        color = emotionColor,
                        modifier = Modifier.padding(horizontal = Dimens.space)
                    )
                }

                Box {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.size(Dimens.icon_size_lg)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(Dimens.icon_size_md)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                onDelete()
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Share") },
                            onClick = {
                                onShare()
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Share, contentDescription = null)
                            }
                        )
                    }
                }
            }

            // Title
            Text(
                userEvent.eventInfo.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = Dimens.space),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Description
            Text(
                userEvent.eventInfo.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = Dimens.space),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            // Tags with improved styling
            if (!userEvent.tags.isNullOrEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.space),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.half_space),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    userEvent.tags!!.forEach { tag ->
                        Surface(
                            modifier = Modifier,
                            shape = RoundedCornerShape(Dimens.corner_xl),
                            color = emotionColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                tag.tagName,
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

            // Media indicator
            if (!userEvent.mediaPaths.isNullOrEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.space),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "📷 ${userEvent.mediaPaths!!.size} photo(s)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ============ PREVIEW COMPOSABLES ============

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun DiaryDashboardScreenPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            DiaryDashboardHeader(onRefreshClick = {})
            EventsList(
                events = createPreviewEvents(),
                onNavigateEdit = {},
                onDelete = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun DiaryDashboardEmptyStatePreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            DiaryDashboardHeader(onRefreshClick = {})
            EmptyEventsScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun DiaryDashboardLoadingStatePreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            DiaryDashboardHeader(onRefreshClick = {})
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun EventCardPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.card_padding_lg)
        ) {
            EventCard(
                userEvent = createPreviewEvent(),
                onClick = {},
                onDelete = {},
                onShare = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun HeaderPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        DiaryDashboardHeader(onRefreshClick = {})
    }
}

// ============ PREVIEW DATA ============

private fun createPreviewEvent(): UserEvent {
    return UserEvent(
        eventInfo = EventInfo(
            eventId = 1L,
            dateInfo = "2026-02-16 16:19:32.461892Z",
            title = "Amazing Day at the Park",
            description = "Had a wonderful time at the park today. The weather was perfect, and I spent time with family. We played games, had ice cream, and made wonderful memories together. The sunset was beautiful!",
            emotion = "Happy"
        ),
        mediaPaths = listOf(
            MediaInfo(1L, "/storage/emulated/0/DCIM/photo1.jpg", 1L),
            MediaInfo(2L, "/storage/emulated/0/DCIM/photo2.jpg", 1L),
            MediaInfo(3L, "/storage/emulated/0/DCIM/photo3.jpg", 1L)
        ),
        tags = listOf(
            TagInfo(1L, "Family", 1L),
            TagInfo(2L, "Fun", 1L),
            TagInfo(3L, "Memories", 1L)
        )
    )
}

private fun createPreviewEvents(): List<UserEvent> {
    return listOf(
        UserEvent(
            eventInfo = EventInfo(
                eventId = 1L,
                dateInfo = "2026-02-16 16:19:32.461892Z",
                title = "Amazing Day at the Park",
                description = "Had a wonderful time at the park today. The weather was perfect, and I spent quality time with family...",
                emotion = "Happy"
            ),
            mediaPaths = listOf(
                MediaInfo(1L, "/storage/emulated/0/DCIM/photo1.jpg", 1L),
                MediaInfo(2L, "/storage/emulated/0/DCIM/photo2.jpg", 1L)
            ),
            tags = listOf(
                TagInfo(1L, "Family", 1L),
                TagInfo(2L, "Fun", 1L)
            )
        ),
        UserEvent(
            eventInfo = EventInfo(
                eventId = 2L,
                dateInfo = "2026-02-15 03:58:27.402534Z",
                title = "Productive Work Day",
                description = "Completed several important projects at work. Feeling accomplished and energized. Had great collaboration with the team...",
                emotion = "Excited"
            ),
            mediaPaths = emptyList(),
            tags = listOf(
                TagInfo(4L, "Work", 2L),
                TagInfo(5L, "Achievement", 2L)
            )
        ),
        UserEvent(
            eventInfo = EventInfo(
                eventId = 3L,
                dateInfo = "2026-02-15 03:53:12.963754Z",
                title = "Relaxing Evening",
                description = "Spent the evening reading a good book and drinking tea. Perfect way to unwind after a busy day...",
                emotion = "Calm"
            ),
            mediaPaths = listOf(
                MediaInfo(3L, "/storage/emulated/0/Pictures/sunset.jpg", 3L)
            ),
            tags = listOf(
                TagInfo(6L, "Relaxation", 3L)
            )
        )
    )
}
