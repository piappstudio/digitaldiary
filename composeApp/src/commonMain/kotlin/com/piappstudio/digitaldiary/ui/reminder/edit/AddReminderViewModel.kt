package com.piappstudio.digitaldiary.ui.reminder.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piappstudio.digitaldiary.database.ReminderRepository
import com.piappstudio.digitaldiary.database.entity.ReminderInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddReminderUiState(
    val title: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val isReminderRequired: Boolean = false,
    val remindBefore: Int? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)

sealed interface AddReminderSideEffect {
    data object NavigateBack : AddReminderSideEffect
}

class AddReminderViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    val uiState: StateFlow<AddReminderUiState>
        field: MutableStateFlow<AddReminderUiState> = MutableStateFlow(AddReminderUiState())

    val sideEffect: SharedFlow<AddReminderSideEffect>
        field: MutableSharedFlow<AddReminderSideEffect> = MutableSharedFlow<AddReminderSideEffect>()

    private var currentReminderId: Long? = null

    fun loadReminder(reminderId: Long?) {
        currentReminderId = if (reminderId == 0L) null else reminderId
        
        uiState.update {
            AddReminderUiState().copy(
                isLoading = currentReminderId != null
            )
        }

        if (currentReminderId != null) {
            viewModelScope.launch {
                val reminderEvent = reminderRepository.getReminderEvent(currentReminderId!!).firstOrNull()
                if (reminderEvent != null) {
                    val info = reminderEvent.reminderInfo
                    uiState.update {
                        it.copy(
                            title = info.title,
                            description = info.description,
                            startDate = info.startDate ?: "",
                            endDate = info.endDate ?: "",
                            isReminderRequired = info.isReminderRequired,
                            remindBefore = info.remindBefore,
                            isLoading = false
                        )
                    }
                } else {
                    uiState.update { it.copy(isLoading = false, error = "Reminder not found") }
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

    fun onStartDateChange(newDate: String) {
        uiState.update { it.copy(startDate = newDate) }
    }

    fun onEndDateChange(newDate: String) {
        uiState.update { it.copy(endDate = newDate) }
    }

    fun onReminderRequiredChange(required: Boolean) {
        uiState.update { it.copy(isReminderRequired = required) }
    }

    fun onRemindBeforeChange(minutes: Int?) {
        uiState.update { it.copy(remindBefore = minutes) }
    }

    fun saveReminder() {
        val state = uiState.value
        if (state.title.isBlank()) {
            uiState.update { it.copy(error = "Title cannot be empty") }
            return
        }

        uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                val reminderInfo = ReminderInfo(
                    reminderId = currentReminderId,
                    title = state.title,
                    description = state.description,
                    startDate = if (state.startDate.isBlank()) null else state.startDate,
                    endDate = if (state.endDate.isBlank()) null else state.endDate,
                    isReminderRequired = state.isReminderRequired,
                    remindBefore = state.remindBefore
                )

                if (currentReminderId == null) {
                    reminderRepository.insert(reminderInfo)
                } else {
                    reminderRepository.update(reminderInfo)
                }

                uiState.update { it.copy(isSaving = false) }
                sideEffect.emit(AddReminderSideEffect.NavigateBack)
            } catch (e: Exception) {
                uiState.update { it.copy(isSaving = false, error = e.message ?: "Failed to save") }
            }
        }
    }
}
