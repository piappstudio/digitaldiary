package com.piappstudio.digitaldiary.ui.diary.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.entity.MediaInfo
import com.piappstudio.digitaldiary.database.entity.UserEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiaryDetailViewModel(val diaryRepository: DiaryRepository) : ViewModel() {
    private val _userEvent = MutableStateFlow<UserEvent?>(null)
    val userEvent: StateFlow<UserEvent?> = _userEvent.asStateFlow()

    private val _medias = MutableStateFlow<List<MediaInfo>>(emptyList())
    val medias: StateFlow<List<MediaInfo>> = _medias.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadEventDetail(eventId: Long) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch(Dispatchers.Default) {
            try {
                diaryRepository.getUserEvent(eventId).collect { userEvent ->
                    _userEvent.value = userEvent
                    Logger.d { "Event loaded: ${userEvent.eventInfo.title}" }
                }
            } catch (e: Exception) {
                Logger.e("DiaryDetailViewModel", e) { "Error loading event detail" }
                _error.value = "Failed to load diary entry"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMedias(eventId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                diaryRepository.getMedias(eventId).collect { mediaList ->
                    _medias.value = mediaList
                }
            } catch (e: Exception) {
                Logger.e("DiaryDetailViewModel", e) { "Error loading medias" }
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}