package com.piappstudio.digitaldiary.ui.reminder.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.ReminderRepository
import com.piappstudio.digitaldiary.database.entity.ReminderEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ReminderDetailUiState(
    val reminderEvent: ReminderEvent? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ReminderDetailViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    val uiState: StateFlow<ReminderDetailUiState>
        field = MutableStateFlow(ReminderDetailUiState())

    fun loadData(reminderId: Long) {
        uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            reminderRepository.getReminderEvent(reminderId)
                .onEach { event ->
                    uiState.update {
                        it.copy(
                            reminderEvent = event,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .catch { e ->
                    Logger.e("ReminderDetailViewModel", e) { "Error loading details for reminderId: $reminderId" }
                    uiState.update {
                        it.copy(
                            error = "Failed to load reminder entry.",
                            isLoading = false
                        )
                    }
                }
                .collect()
        }
    }

    fun deleteReminder(reminderId: Long, onDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                val event = uiState.value.reminderEvent
                if (event != null) {
                    reminderRepository.delete(event.reminderInfo)
                    onDeleted()
                }
            } catch (e: Exception) {
                Logger.e("ReminderDetailViewModel", e) { "Error deleting reminder" }
            }
        }
    }
}
