package com.piappstudio.digitaldiary.ui.reminder

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.DigitalDiaryTheme
import com.piappstudio.digitaldiary.common.theme.DiaryMood
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.database.entity.ReminderEvent
import com.piappstudio.digitaldiary.database.entity.ReminderInfo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ReminderDashboardScreen(
    viewModel: ReminderDashboardViewModel = koinViewModel()
) {
    val reminders by viewModel.reminders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ReminderDashboardHeader(
            onRefreshClick = { viewModel.refreshReminders() }
        )

        if (isLoading && reminders.isEmpty()) {
            LoadingScreen()
        } else if (reminders.isEmpty()) {
            EmptyRemindersScreen()
        } else {
            RemindersList(
                reminders = reminders,
                onDeleteReminder = { viewModel.deleteReminder(it) }
            )
        }
    }
}

@Composable
private fun ReminderDashboardHeader(
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(Dimens.card_padding_lg)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Upcoming Events",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    "Stay on track with your schedule",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                )
            }

            IconButton(onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh reminders",
                    tint = MaterialTheme.colorScheme.onSecondary,
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
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(Dimens.icon_size_xl)
        )
    }
}

@Composable
private fun EmptyRemindersScreen() {
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
                    shape = RoundedCornerShape(Dimens.corner_lg)
                )
                .padding(Dimens.card_padding_lg)
        ) {
            Text(
                "🎯",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = Dimens.big_space)
            )
            Text(
                "No upcoming reminders",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Create a reminder to stay organized!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RemindersList(
    reminders: List<ReminderEvent>,
    onDeleteReminder: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.card_padding_md),
        verticalArrangement = Arrangement.spacedBy(Dimens.big_space)
    ) {
        items(reminders) { reminderEvent ->
            ReminderCard(
                reminderEvent = reminderEvent,
                onDelete = { onDeleteReminder(reminderEvent.reminderInfo.reminderId ?: 0L) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(Dimens.big_space))
        }
    }
}

@Composable
private fun ReminderCard(
    reminderEvent: ReminderEvent,
    onDelete: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val priority = determinePriority(reminderEvent.reminderInfo.endDate)
    val priorityColor = when (priority) {
        ReminderPriority.URGENT -> getTemplateColor(4) // Red
        ReminderPriority.HIGH -> getTemplateColor(1)   // Orange
        ReminderPriority.MEDIUM -> getTemplateColor(2) // Teal
        ReminderPriority.LOW -> getTemplateColor(5)    // Cyan
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(RoundedCornerShape(Dimens.corner_lg))
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.elevation_4)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.card_padding_lg)
        ) {
            // Header with priority indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.big_space),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Priority badge
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(priorityColor, shape = RoundedCornerShape(2.dp))
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = Dimens.space)
                    ) {
                        Text(
                            reminderEvent.reminderInfo.endDate ?: "No date",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            priority.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = priorityColor
                        )
                    }
                }

                IconButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.size(Dimens.icon_size_lg)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete reminder",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(Dimens.icon_size_sm)
                    )
                }
            }

            // Title and Duration
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.space),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    reminderEvent.reminderInfo.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Days Available calculation (Compact view)
                val daysAvailable = calculateDays(reminderEvent.reminderInfo.startDate, reminderEvent.reminderInfo.endDate)
                if (daysAvailable != null) {
                    Surface(
                        shape = RoundedCornerShape(Dimens.corner_full),
                        color = priorityColor.copy(alpha = 0.1f),
                        modifier = Modifier.padding(start = Dimens.space)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = Dimens.space, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = priorityColor,
                                modifier = Modifier.size(Dimens.icon_size_xs)
                            )
                            Text(
                                " ${daysAvailable}d",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = priorityColor
                            )
                        }
                    }
                }
            }

            // Description
            if (reminderEvent.reminderInfo.description.isNotEmpty()) {
                Text(
                    reminderEvent.reminderInfo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = Dimens.space),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Timeline info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.space),
                horizontalArrangement = Arrangement.spacedBy(Dimens.space)
            ) {
                reminderEvent.reminderInfo.startDate?.let {
                    TimelineChip(
                        label = "From",
                        date = it,
                        icon = Icons.Default.DateRange,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }

                reminderEvent.reminderInfo.endDate?.let {
                    TimelineChip(
                        label = "Until",
                        date = it,
                        icon = Icons.Default.Schedule,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            // Reminder notification info
            if (reminderEvent.reminderInfo.isReminderRequired && reminderEvent.reminderInfo.remindBefore != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.big_space)
                        .background(
                            priorityColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(Dimens.corner_sm)
                        )
                        .padding(Dimens.space),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification",
                        tint = priorityColor,
                        modifier = Modifier.size(Dimens.icon_size_sm)
                    )
                    Text(
                        "  Remind me ${reminderEvent.reminderInfo.remindBefore} minutes before",
                        style = MaterialTheme.typography.labelSmall,
                        color = priorityColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Edit button (shown when expanded)
            if (isExpanded) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = Dimens.big_space),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(Dimens.corner_sm)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit reminder",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .size(Dimens.icon_size_sm)
                            .padding(end = Dimens.half_space)
                    )
                    Text("Edit", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Reminder") },
            text = { Text("Are you sure you want to delete this reminder?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun calculateDays(startDate: String?, endDate: String?): Int? {
    if (startDate == null || endDate == null) return null
    return try {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        start.daysUntil(end)
    } catch (e: Exception) {
        null
    }
}

@Composable
private fun TimelineChip(
    label: String,
    date: String,
    icon: ImageVector,
    color: Color
) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(Dimens.corner_sm),
        color = color.copy(alpha = 0.05f)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimens.space,
                vertical = Dimens.half_space
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(Dimens.icon_size_xs)
            )
            Text(
                " $label: $date",
                style = MaterialTheme.typography.labelSmall,
                color = color,
                modifier = Modifier.padding(start = Dimens.half_space)
            )
        }
    }
}

// ============ PREVIEW COMPOSABLES ============

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ReminderDashboardScreenPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ReminderDashboardHeader(onRefreshClick = {})
            RemindersList(
                reminders = createPreviewReminders(),
                onDeleteReminder = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ReminderDashboardEmptyStatePreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ReminderDashboardHeader(onRefreshClick = {})
            EmptyRemindersScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ReminderDashboardLoadingStatePreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ReminderDashboardHeader(onRefreshClick = {})
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ReminderCardPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.card_padding_lg)
        ) {
            ReminderCard(
                reminderEvent = createPreviewReminder(),
                onDelete = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ReminderHeaderPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        ReminderDashboardHeader(onRefreshClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun TimelineChipPreview() {
    DigitalDiaryTheme(diaryMood = DiaryMood.PASSIONATE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.card_padding_lg),
            verticalArrangement = Arrangement.spacedBy(Dimens.space)
        ) {
            TimelineChip(
                label = "From",
                date = "2026-02-15",
                icon = Icons.Default.DateRange,
                color = getTemplateColor(0)
            )
            TimelineChip(
                label = "Until",
                date = "2026-02-20",
                icon = Icons.Default.Schedule,
                color = getTemplateColor(1)
            )
            TimelineChip(
                label = "From",
                date = "2026-02-25",
                icon = Icons.Default.DateRange,
                color = getTemplateColor(4)
            )
        }
    }
}

// ============ PREVIEW DATA ============

private fun createPreviewReminder(): ReminderEvent {
    return ReminderEvent(
        reminderInfo = ReminderInfo(
            reminderId = 1L,
            title = "Team Meeting",
            description = "Important quarterly planning meeting with the entire team. Please come prepared with project updates.",
            startDate = "2026-02-16",
            endDate = "2026-02-16",
            isReminderRequired = true,
            remindBefore = 30
        )
    )
}

private fun createPreviewReminders(): List<ReminderEvent> {
    return listOf(
        ReminderEvent(
            reminderInfo = ReminderInfo(
                reminderId = 1L,
                title = "Team Meeting",
                description = "Important quarterly planning meeting with the entire team.",
                startDate = "2026-02-16",
                endDate = "2026-02-16",
                isReminderRequired = true,
                remindBefore = 30
            )
        ),
        ReminderEvent(
            reminderInfo = ReminderInfo(
                reminderId = 2L,
                title = "Project Deadline",
                description = "Final submission deadline for the mobile app project.",
                startDate = "2026-02-18",
                endDate = "2026-02-20",
                isReminderRequired = true,
                remindBefore = 60
            )
        ),
        ReminderEvent(
            reminderInfo = ReminderInfo(
                reminderId = 3L,
                title = "Doctor's Appointment",
                description = "Annual checkup with Dr. Smith at 2:00 PM.",
                startDate = "2026-02-22",
                endDate = "2026-02-22",
                isReminderRequired = true,
                remindBefore = 120
            )
        ),
        ReminderEvent(
            reminderInfo = ReminderInfo(
                reminderId = 4L,
                title = "Vacation Planning",
                description = "Plan summer vacation and book flights.",
                startDate = "2026-02-25",
                endDate = "2026-03-05",
                isReminderRequired = false,
                remindBefore = null
            )
        )
    )
}