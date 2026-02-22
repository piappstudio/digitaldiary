package com.piappstudio.digitaldiary.ui.diary.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.getTemplateColor
import com.piappstudio.digitaldiary.ui.component.PiActionIcon
import com.piappstudio.digitaldiary.ui.component.PiHeader
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddDiaryScreen(
    eventId: Long? = null,
    onBack: () -> Unit,
    viewModel: AddDiaryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showTagDialog by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var showGallery by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

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

                    // Title Input
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { viewModel.onTitleChange(it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(Dimens.corner_md)
                    )

                    // Date Selection Input
                    val formattedDate = remember(uiState.dateMillis) {
                        val instant = Instant.fromEpochMilliseconds(uiState.dateMillis)
                        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                        "${dateTime.dayOfMonth}/${dateTime.monthNumber}/${dateTime.year}"
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = formattedDate,
                            onValueChange = { },
                            label = { Text("Date") },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            readOnly = true,
                            trailingIcon = {
                                Icon(Icons.Default.CalendarMonth, contentDescription = "Pick Date")
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = MaterialTheme.colorScheme.outline,
                                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            shape = RoundedCornerShape(Dimens.corner_md)
                        )
                        // Invisible clickable layer over the disabled TextField
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { showDatePicker = true }
                        )
                    }

                    // Description Input
                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.onDescriptionChange(it) },
                        label = { Text("How was your day?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp),
                        minLines = 8,
                        shape = RoundedCornerShape(Dimens.corner_md)
                    )

                    // Action Toolbar: intuitive way to add tags and images
                    ActionToolbar(
                        onAddTagClick = { showTagDialog = true },
                        onCameraClick = { showCamera = true },
                        onGalleryClick = { showGallery = true }
                    )

                    // Previews
                    if (uiState.capturedPhoto != null || uiState.selectedImages.isNotEmpty() || uiState.images.isNotEmpty() || uiState.existingImages.isNotEmpty()) {
                        ImagesPreviewList(
                            capturedPhoto = uiState.capturedPhoto,
                            selectedImages = uiState.selectedImages,
                            byteImages = uiState.images,
                            existingImages = uiState.existingImages,
                            onRemoveCaptured = { viewModel.removeCapturedPhoto() },
                            onRemoveSelected = { viewModel.removeSelectedImage(it) },
                            onRemoveByteImage = { viewModel.removeImage(it) },
                            onRemoveExisting = { viewModel.removeExistingImage(it) }
                        )
                    }

                    // Tags Preview Row
                    if (uiState.tags.isNotEmpty()) {
                        TagsPreviewRow(
                            tags = uiState.tags,
                            onRemove = { viewModel.removeTag(it) }
                        )
                    }

                    // Emotion Selection
                    Column(verticalArrangement = Arrangement.spacedBy(Dimens.space)) {
                        Text("Mood", style = MaterialTheme.typography.titleMedium)
                        EmotionPicker(
                            selectedEmotion = uiState.emotion,
                            onEmotionSelected = { viewModel.onEmotionChange(it) }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.biggest_space))
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = uiState.dateMillis)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { viewModel.onDateChange(it) }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTagDialog) {
        AddTagDialog(
            onDismiss = { showTagDialog = false },
            onConfirm = { 
                viewModel.addTag(it)
                showTagDialog = false
            }
        )
    }

    if (showCamera) {
        ImagePickerLauncher(
            config = ImagePickerConfig(
                onPhotoCaptured = { result ->
                    viewModel.onPhotoCaptured(result)
                    showCamera = false
                },
                onError = { showCamera = false },
                onDismiss = { showCamera = false }
            )
        )
    }
    if (showGallery) {
        GalleryPickerLauncher(
            onPhotosSelected = { photos ->
                viewModel.onImagesSelected(photos)
                showGallery = false
            },
            onError = { showGallery = false },
            onDismiss = { showGallery = false },
            allowMultiple = true
        )
    }
}

@Composable
fun ActionToolbar(
    onAddTagClick: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(Dimens.corner_md)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.space),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionToolButton(
                icon = Icons.AutoMirrored.Filled.Label,
                label = "Add Tag",
                onClick = onAddTagClick
            )
            VerticalDivider(modifier = Modifier.height(24.dp), color = MaterialTheme.colorScheme.outlineVariant)
            ActionToolButton(
                icon = Icons.Default.PhotoCamera,
                label = "Camera",
                onClick = onCameraClick
            )
            VerticalDivider(modifier = Modifier.height(24.dp), color = MaterialTheme.colorScheme.outlineVariant)
            ActionToolButton(
                icon = Icons.Default.Image,
                label = "Gallery",
                onClick = onGalleryClick
            )
        }
    }
}

@Composable
fun ActionToolButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.corner_sm))
            .clickable { onClick() }
            .padding(horizontal = Dimens.space, vertical = Dimens.half_space)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ImagesPreviewList(
    capturedPhoto: PhotoResult?,
    selectedImages: List<GalleryPhotoResult>,
    byteImages: List<ByteArray>,
    existingImages: List<String>,
    onRemoveCaptured: () -> Unit,
    onRemoveSelected: (GalleryPhotoResult) -> Unit,
    onRemoveByteImage: (Int) -> Unit,
    onRemoveExisting: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.half_space)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(Dimens.half_space))
            Text("Photos", style = MaterialTheme.typography.titleSmall)
        }
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.space),
            contentPadding = PaddingValues(vertical = Dimens.half_space)
        ) {
            itemsIndexed(existingImages) { _, path ->
                PhotoPreviewItem(
                    photo = path,
                    onRemove = { onRemoveExisting(path) }
                )
            }
            if (capturedPhoto != null) {
                item {
                    PhotoPreviewItem(
                        photo = capturedPhoto,
                        onRemove = onRemoveCaptured
                    )
                }
            }
            itemsIndexed(selectedImages) { _, photo ->
                PhotoPreviewItem(
                    photo = photo,
                    onRemove = { onRemoveSelected(photo) }
                )
            }
            itemsIndexed(byteImages) { index, data ->
                PhotoPreviewItem(
                    photo = data,
                    onRemove = { onRemoveByteImage(index) }
                )
            }
        }
    }
}

@Composable
fun PhotoPreviewItem(
    photo: Any,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .shadow(2.dp, RoundedCornerShape(Dimens.corner_md))
            .clip(RoundedCornerShape(Dimens.corner_md))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        when (photo) {
            is String -> {
                AsyncImage(model = photo, modifier = Modifier.fillMaxSize(), contentDescription = null, contentScale = ContentScale.Crop)
            }
            is GalleryPhotoResult -> {
                AsyncImage(model = photo.uri, modifier = Modifier.fillMaxSize(), contentDescription = null, contentScale = ContentScale.Crop)
            }
            is ByteArray -> {
                Image(
                    bitmap = photo.decodeToImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Icon(
                    Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center).size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        }
        
        // Intuitive Delete button in the corner - Clear intent with error color
        IconButton(
            onClick = onRemove,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(28.dp)
                .background(MaterialTheme.colorScheme.error.copy(alpha = 0.9f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsPreviewRow(
    tags: List<String>,
    onRemove: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.half_space)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.Label, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(Dimens.half_space))
            Text("Tags", style = MaterialTheme.typography.titleSmall)
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.half_space)
        ) {
            tags.forEach { tag ->
                InputChip(
                    selected = true,
                    onClick = { onRemove(tag) },
                    label = { Text(tag, style = MaterialTheme.typography.labelSmall) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove",
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    colors = InputChipDefaults.inputChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@Composable
fun AddTagDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Tag") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter tag name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.corner_md)
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(text) },
                enabled = text.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
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

            FilterChip(
                selected = isSelected,
                onClick = { onEmotionSelected(emotion) },
                label = { 
                    Text(
                        text = emotion,
                        style = MaterialTheme.typography.labelMedium
                    ) 
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = emotionColor,
                    selectedLabelColor = Color.White,
                    containerColor = emotionColor.copy(alpha = 0.1f),
                    labelColor = emotionColor
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = emotionColor.copy(alpha = 0.5f),
                    selectedBorderColor = Color.Transparent,
                    borderWidth = 1.dp,
                    selectedBorderWidth = 0.dp
                ),
                shape = RoundedCornerShape(Dimens.corner_full)
            )
        }
    }
}
