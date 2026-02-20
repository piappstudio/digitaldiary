package com.piappstudio.digitaldiary.ui.diary.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * UI State for the Diary Detail screen.
 */
data class DiaryDetailUiState(
    val userEvent: UserEvent? = null,
    val medias: List<MediaInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class DiaryDetailViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {

    // Using a cleaner approach for State management in Kotlin 2.x
    // Explicit backing fields (field) is a new feature that simplifies the _private / public pattern
    val uiState: StateFlow<DiaryDetailUiState>
        field = MutableStateFlow(DiaryDetailUiState())

    /**
     * Consolidated loading function using combine for reactivity.
     */
    fun loadData(eventId: Long) {
        uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            combine(
                diaryRepository.getUserEvent(eventId),
                diaryRepository.getMedias(eventId)
            ) { event, mediaList ->
                uiState.update {
                    it.copy(
                        userEvent = event, 
                        medias = mediaList, 
                        isLoading = false,
                        error = null
                    ) 
                }
            }.catch { e ->
                Logger.e("DiaryDetailViewModel", e) { "Error loading details for eventId: $eventId" }
                uiState.update {
                    it.copy(
                        error = "Failed to load diary entry.", 
                        isLoading = false 
                    ) 
                }
            }.collect()
        }
    }

    fun clearError() {
        uiState.update { it.copy(error = null) }
    }
}
