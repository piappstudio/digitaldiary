package com.piappstudio.digitaldiary.ui.diary.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.FileStorage
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.TagInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

data class AddDiaryUiState(
    val title: String = "",
    val description: String = "",
    val emotion: String = "Happy",
    val tags: List<String> = emptyList(),
    val images: List<ByteArray> = emptyList(), // Store images as bytes for KMP cross-platform handling
    val capturedPhoto: PhotoResult? = null,
    val selectedImages: List<GalleryPhotoResult> = emptyList(),
    val existingImages: List<String> = emptyList(),
    val dateMillis: Long = Clock.System.now().toEpochMilliseconds(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)

sealed interface AddDiarySideEffect {
    data object NavigateBack : AddDiarySideEffect
}

class AddDiaryViewModel(
    private val diaryRepository: DiaryRepository,
    private val fileStorage: FileStorage
) : ViewModel() {

    // Explicit backing field for state
    val uiState: StateFlow<AddDiaryUiState>
        field: MutableStateFlow<AddDiaryUiState> = MutableStateFlow(AddDiaryUiState())

    val sideEffect: SharedFlow<AddDiarySideEffect>
        field: MutableSharedFlow<AddDiarySideEffect> = MutableSharedFlow<AddDiarySideEffect>()

    private var currentEventId: Long? = null

    fun loadEvent(eventId: Long?) {
        currentEventId = if (eventId == 0L || eventId == null) null else eventId
        
        uiState.update { state ->
            AddDiaryUiState().copy(
                isLoading = currentEventId != null,
                title = if (currentEventId == null) "" else state.title,
                description = if (currentEventId == null) "" else state.description,
                emotion = if (currentEventId == null) "Happy" else state.emotion,
                tags = if (currentEventId == null) emptyList() else state.tags,
                dateMillis = Clock.System.now().toEpochMilliseconds()
            ) 
        }

        if (currentEventId != null) {
            viewModelScope.launch {
                val event = diaryRepository.getUserEvent(currentEventId!!).firstOrNull()
                if (event != null) {
                    // Try to parse existing date string to millis if possible
                    val existingDateMillis = try {
                        val localDateTime = LocalDateTime.parse(event.eventInfo.dateInfo)
                        val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
                        instant.toEpochMilliseconds()
                    } catch (e: Exception) {
                        Clock.System.now().toEpochMilliseconds()
                    }

                    uiState.update { state ->
                        state.copy(
                            title = event.eventInfo.title,
                            description = event.eventInfo.description,
                            emotion = event.eventInfo.emotion,
                            tags = event.tags?.map { it.tagName } ?: emptyList(),
                            existingImages = event.mediaPaths?.map { it.mediaPath } ?: emptyList(),
                            dateMillis = existingDateMillis,
                            isLoading = false
                        )
                    }
                } else {
                    uiState.update { it.copy(isLoading = false, error = "Entry not found") }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        uiState.update { it.copy(title = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        uiState.update { it.copy(description = newDescription) }
    }

    fun onEmotionChange(newEmotion: String) {
        uiState.update { it.copy(emotion = newEmotion) }
    }

    fun onDateChange(millis: Long) {
        uiState.update { it.copy(dateMillis = millis, error = null) }
    }

    fun addTag(tag: String) {
        val trimmed = tag.trim()
        if (trimmed.isEmpty()) return
        uiState.update { state ->
            if (state.tags.contains(trimmed)) state else state.copy(tags = state.tags + trimmed)
        }
    }

    fun removeTag(tag: String) {
        uiState.update { state ->
            state.copy(tags = state.tags - tag)
        }
    }

    fun addImage(image: ByteArray) {
        uiState.update { state ->
            state.copy(images = state.images + image)
        }
    }

    fun removeImage(index: Int) {
        uiState.update { state ->
            val newList = state.images.toMutableList()
            if (index in newList.indices) {
                newList.removeAt(index)
            }
            state.copy(images = newList)
        }
    }

    fun onPhotoCaptured(result: PhotoResult) {
        uiState.update { it.copy(capturedPhoto = result) }
    }

    fun removeCapturedPhoto() {
        uiState.update { it.copy(capturedPhoto = null) }
    }

    fun onImagesSelected(photos: List<GalleryPhotoResult>) {
        uiState.update { it.copy(selectedImages = it.selectedImages + photos) }
    }

    fun removeSelectedImage(photo: GalleryPhotoResult) {
        uiState.update { it.copy(selectedImages = it.selectedImages - photo) }
    }

    fun removeExistingImage(path: String) {
        uiState.update { it.copy(existingImages = it.existingImages - path) }
    }

    fun saveEntry() {
        val state = uiState.value
        
        // Validation
        if (state.title.isBlank() || state.description.isBlank()) {
            uiState.update { it.copy(error = "Please fill in all fields") }
            return
        }
        
        // Date validation: Should not be in the future (allow some buffer for clock skew)
        val nowMillis = Clock.System.now().toEpochMilliseconds()
        if (state.dateMillis > nowMillis + 300000) { // 5 minutes buffer
             uiState.update { it.copy(error = "Date cannot be in the future") }
             return
        }

        uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                // Use the selected date from the state
                val selectedInstant = Instant.fromEpochMilliseconds(state.dateMillis)
                val selectedDate = selectedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
                val dateInfo = selectedDate.toString()

                val eventInfo = EventInfo(
                    eventId = currentEventId,
                    title = state.title,
                    description = state.description,
                    emotion = state.emotion,
                    dateInfo = dateInfo
                )

                // Convert state back to entities
                val tags = state.tags.map { TagInfo(tagId = 0L, tagName = it, eventKey = currentEventId ?: 0L) }
                
                // Save images to internal storage
                val savedMediaPaths = state.existingImages.toMutableList()
                
                val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
                
                // 1. Save captured photo
                state.capturedPhoto?.let { photo ->
                    val bytes = fileStorage.readBytes(photo.uri)
                    if (bytes != null) {
                        val fileName = "images/Digital_Diary_${currentTimeMillis}.jpg"
                        fileStorage.saveImage(bytes, fileName)?.let { savedMediaPaths.add(it) }
                    }
                }
                
                // 2. Save selected gallery images
                state.selectedImages.forEachIndexed { index, photo ->
                    val bytes = fileStorage.readBytes(photo.uri)
                    if (bytes != null) {
                        val fileName = "images/Digital_Diary_${currentTimeMillis}_${index}.jpg"
                        fileStorage.saveImage(bytes, fileName)?.let { savedMediaPaths.add(it) }
                    }
                }
                
                // 3. Save ByteArrays
                state.images.forEachIndexed { index, bytes ->
                    val fileName = "images/Digital_Diary_manual_${currentTimeMillis}_${index}.jpg"
                    fileStorage.saveImage(bytes, fileName)?.let { savedMediaPaths.add(it) }
                }

                val mediaInfos = savedMediaPaths.map {
                    MediaInfo(mediaId = null, mediaPath = it, eventKey = currentEventId ?: 0L)
                }

                val userEvent = UserEvent(eventInfo, mediaInfos, tags)
                if (currentEventId == null) {
                    diaryRepository.insert(userEvent)
                } else {
                    diaryRepository.updateFullEvent(userEvent)
                }

                uiState.update { it.copy(isSaving = false) }
                sideEffect.emit(AddDiarySideEffect.NavigateBack)
            } catch (e: Exception) {
                Logger.e("AddDiaryViewModel", e) { "Error saving diary entry" }
                uiState.update { it.copy(isSaving = false, error = e.message ?: "Failed to save") }
            }
        }
    }
}
