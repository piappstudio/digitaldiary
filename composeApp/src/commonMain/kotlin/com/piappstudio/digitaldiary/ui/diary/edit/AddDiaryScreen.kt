package com.piappstudio.digitaldiary.ui.diary.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.ui.component.PiActionIcon
import com.piappstudio.digitaldiary.ui.component.PiHeader
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddDiaryScreen(
    eventId: Long? = null,
    onBack: () -> Unit,
    viewModel: AddDiaryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(eventId) {
        viewModel.loadEvent(eventId)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is AddDiarySideEffect.NavigateBack -> onBack()
            }
        }
    }

    val colorIndex = uiState.emotion.hashCode().rem(6).coerceAtLeast(0)
    val emotionColor = getTemplateColor(colorIndex)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PiHeader(
                title = if (eventId == null) "New Entry" else "Edit Entry",
                onBackClick = onBack,
                backgroundColor = emotionColor,
                actions = {
                    PiActionIcon(
                        icon = Icons.Default.Check,
                        onClick = { viewModel.saveEntry() },
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
                            .heightIn(min = 150.dp),
                        minLines = 5
                    )

                    Text("How are you feeling?", style = MaterialTheme.typography.titleMedium)

                    EmotionPicker(
                        selectedEmotion = uiState.emotion,
                        onEmotionSelected = { viewModel.onEmotionChange(it) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmotionPicker(
    selectedEmotion: String,
    onEmotionSelected: (String) -> Unit
) {
    val emotions = listOf("Happy", "Excited", "Inspired", "Calm", "Peaceful", "Anxious", "Sad", "Grateful")
    
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.space),
        verticalArrangement = Arrangement.spacedBy(Dimens.space)
    ) {
        emotions.forEach { emotion ->
            val isSelected = emotion == selectedEmotion
            val colorIndex = emotion.hashCode().rem(6).coerceAtLeast(0)
            val emotionColor = getTemplateColor(colorIndex)

            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.corner_full))
                    .clickable { onEmotionSelected(emotion) },
                color = if (isSelected) emotionColor else emotionColor.copy(alpha = 0.1f),
                border = if (isSelected) null else BorderStroke(1.dp, emotionColor.copy(alpha = 0.5f))
            ) {
                Text(
                    text = emotion,
                    modifier = Modifier.padding(horizontal = Dimens.card_padding_md, vertical = Dimens.space),
                    color = if (isSelected) Color.White else emotionColor,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
