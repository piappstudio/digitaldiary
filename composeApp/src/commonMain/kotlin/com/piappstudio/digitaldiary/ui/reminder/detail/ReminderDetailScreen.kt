package com.piappstudio.digitaldiary.ui.reminder.detail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.database.entity.ReminderEvent
import com.piappstudio.digitaldiary.ui.component.PiActionIcon
import com.piappstudio.digitaldiary.ui.component.PiHeader
import com.piappstudio.digitaldiary.ui.reminder.determinePriority
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReminderDetailScreen(
    reminderId: Long,
    onBackClick: () -> Unit,
    onNavigateEdit: (Long) -> Unit,
    viewModel: ReminderDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val reminderEvent = uiState.reminderEvent
    val isLoading = uiState.isLoading
    val error = uiState.error

    LaunchedEffect(reminderId) {
        viewModel.loadData(reminderId)
    }

    if (isLoading) {
        LoadingDetailScreen()
    } else if (error != null) {
        // Simple error view
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    } else if (reminderEvent != null) {
        DetailContent(
            reminderEvent = reminderEvent,
            onBackClick = onBackClick,
            onEdit = { onNavigateEdit(reminderId) },
            onDelete = {
                viewModel.deleteReminder(reminderId) {
                    onBackClick()
                }
            }
        )
    }
}

@Composable
private fun DetailContent(
    reminderEvent: ReminderEvent,
    onBackClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val priority = determinePriority(reminderEvent.reminderInfo.endDate)
    val priorityColor = when (priority) {
        com.piappstudio.digitaldiary.ui.reminder.ReminderPriority.URGENT -> getTemplateColor(4)
        com.piappstudio.digitaldiary.ui.reminder.ReminderPriority.HIGH -> getTemplateColor(1)
        com.piappstudio.digitaldiary.ui.reminder.ReminderPriority.MEDIUM -> getTemplateColor(2)
        com.piappstudio.digitaldiary.ui.reminder.ReminderPriority.LOW -> getTemplateColor(5)
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            PiHeader(
                title = "Reminder Details",
                backgroundColor = priorityColor,
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

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.card_padding_lg)
            ) {
                Text(
                    reminderEvent.reminderInfo.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = Dimens.space)
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.corner_sm),
                    color = priorityColor.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(Dimens.space),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            priority.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = priorityColor
                        )
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.card_padding_lg)
            ) {
                Text(
                    "Description",
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
                        reminderEvent.reminderInfo.description.ifEmpty { "No description provided." },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(Dimens.card_padding_md),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.card_padding_lg),
                verticalArrangement = Arrangement.spacedBy(Dimens.space)
            ) {
                Text(
                    "Timeline",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                reminderEvent.reminderInfo.startDate?.let {
                    TimelineInfoRow(label = "Start Date", value = it, icon = Icons.Default.DateRange, color = priorityColor)
                }
                reminderEvent.reminderInfo.endDate?.let {
                    TimelineInfoRow(label = "End Date", value = it, icon = Icons.Default.Schedule, color = priorityColor)
                }

                if (reminderEvent.reminderInfo.isReminderRequired) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(Dimens.corner_sm),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(Dimens.card_padding_md),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                "  Reminder set for ${reminderEvent.reminderInfo.remindBefore ?: 0} minutes before",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimens.biggest_space))
        }
    }

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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun TimelineInfoRow(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(Dimens.icon_size_sm))
        Text("  $label: ", style = MaterialTheme.typography.bodyMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun LoadingDetailScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
