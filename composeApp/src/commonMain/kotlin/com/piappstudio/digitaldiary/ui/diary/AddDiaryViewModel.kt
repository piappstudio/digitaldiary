package com.piappstudio.digitaldiary.ui.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.entity.EventInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class AddDiaryUiState(
    val title: String = "",
    val description: String = "",
    val emotion: String = "Happy",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)

sealed interface AddDiarySideEffect {
    data object NavigateBack : AddDiarySideEffect
}

class AddDiaryViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {

    // Using Kotlin 2.3+ Explicit Backing Field
    val uiState: StateFlow<AddDiaryUiState>
        field: MutableStateFlow<AddDiaryUiState> = MutableStateFlow(AddDiaryUiState())

    val sideEffect: SharedFlow<AddDiarySideEffect>
        field: MutableSharedFlow<AddDiarySideEffect> = MutableSharedFlow<AddDiarySideEffect>()

    private var currentEventId: Long? = null

    fun loadEvent(eventId: Long?) {
        // Reset state for new entry or load existing
        currentEventId = if (eventId == 0L) null else eventId
        
        uiState.update {
            AddDiaryUiState().copy(
                isLoading = currentEventId != null,
                title = if (currentEventId == null) "" else it.title,
                description = if (currentEventId == null) "" else it.description,
                emotion = if (currentEventId == null) "Happy" else it.emotion
            ) 
        }

        if (currentEventId != null) {
            viewModelScope.launch {
                val event = diaryRepository.getUserEvent(currentEventId!!).firstOrNull()
                if (event != null) {
                    uiState.update {
                        it.copy(
                            title = event.eventInfo.title,
                            description = event.eventInfo.description,
                            emotion = event.eventInfo.emotion,
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

    fun saveEntry() {
        val state = uiState.value
        if (state.title.isBlank() || state.description.isBlank()) {
            uiState.update { it.copy(error = "Please fill in all fields") }
            return
        }

        uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val dateInfo = now.toString()

                val eventInfo = EventInfo(
                    eventId = currentEventId,
                    title = state.title,
                    description = state.description,
                    emotion = state.emotion,
                    dateInfo = dateInfo
                )

                if (currentEventId == null) {
                    diaryRepository.insert(UserEvent(eventInfo, emptyList(), emptyList()))
                } else {
                    diaryRepository.updateEventInfo(eventInfo)
                }

                uiState.update { it.copy(isSaving = false) }
                sideEffect.emit(AddDiarySideEffect.NavigateBack)
            } catch (e: Exception) {
                uiState.update { it.copy(isSaving = false, error = e.message ?: "Failed to save") }
            }
        }
    }
}
