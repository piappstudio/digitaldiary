package com.piappstudio.digitaldiary.ui.reminder.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.ui.component.PiActionIcon
import com.piappstudio.digitaldiary.ui.component.PiHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddReminderScreen(
    reminderId: Long? = null,
    onBack: () -> Unit,
    viewModel: AddReminderViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(reminderId) {
        viewModel.loadReminder(reminderId)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is AddReminderSideEffect.NavigateBack -> onBack()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PiHeader(
                title = if (reminderId == null) "New Reminder" else "Edit Reminder",
                onBackClick = onBack,
                actions = {
                    PiActionIcon(
                        icon = Icons.Default.Check,
                        onClick = { viewModel.saveReminder() },
                        isLoading = uiState.isSaving,
                        contentDescription = "Save"
                    )
                }
            )

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.card_padding_lg)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(Dimens.bigger_space)
                ) {
                    if (uiState.error != null) {
                        Text(
                            text = uiState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { viewModel.onTitleChange(it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.onDescriptionChange(it) },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        minLines = 3
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.space)
                    ) {
                        OutlinedTextField(
                            value = uiState.startDate,
                            onValueChange = { viewModel.onStartDateChange(it) },
                            label = { Text("Start Date (YYYY-MM-DD)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = uiState.endDate,
                            onValueChange = { viewModel.onEndDateChange(it) },
                            label = { Text("End Date (YYYY-MM-DD)") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Remind Me", style = MaterialTheme.typography.bodyLarge)
                        Switch(
                            checked = uiState.isReminderRequired,
                            onCheckedChange = { viewModel.onReminderRequiredChange(it) }
                        )
                    }

                    if (uiState.isReminderRequired) {
                        OutlinedTextField(
                            value = uiState.remindBefore?.toString() ?: "",
                            onValueChange = { 
                                val minutes = it.toIntOrNull()
                                viewModel.onRemindBeforeChange(minutes)
                            },
                            label = { Text("Remind before (minutes)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
