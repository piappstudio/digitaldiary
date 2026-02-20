package com.piappstudio.digitaldiary.ui.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.ReminderRepository
import com.piappstudio.digitaldiary.database.entity.ReminderEvent
import com.piappstudio.digitaldiary.database.pojo.ReminderFilterOption
import com.piappstudio.digitaldiary.database.pojo.ReminderSortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReminderDashboardViewModel(val reminderRepository: ReminderRepository) : ViewModel() {
    private val _reminders = MutableStateFlow<List<ReminderEvent>>(emptyList())
    val reminders: StateFlow<List<ReminderEvent>> = _reminders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        Logger.d("ReminderDashboardViewModel init $this")
        loadReminders()
    }

    private fun loadReminders() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val filterOption = ReminderFilterOption(
                    sortOrder = ReminderSortOrder.EVENT_ASC,
                    query = null
                )
                reminderRepository.getAllReminders(filterOption).collect { reminderEvent ->
                    val currentList = _reminders.value.toMutableList()
                    currentList.addAll(reminderEvent)
                    // Sort by end date to show upcoming events first
                    currentList.sortBy { it.reminderInfo.endDate }
                    _reminders.value = currentList
                }
            } catch (e: Exception) {
                Logger.e("ReminderDashboardViewModel", e) { "Error loading reminders" }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshReminders() {
        _reminders.value = emptyList()
        loadReminders()
    }

    fun deleteReminder(reminderId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val reminder = _reminders.value.find { it.reminderInfo.reminderId == reminderId }
                reminder?.let {
                    reminderRepository.delete(it.reminderInfo)
                    _reminders.value = _reminders.value.filter { r -> r.reminderInfo.reminderId != reminderId }
                }
            } catch (e: Exception) {
                Logger.e("ReminderDashboardViewModel", e) { "Error deleting reminder" }
            }
        }
    }
}


enum class ReminderPriority(val label: String) {
    URGENT("🔴 Urgent"),
    HIGH("🟠 High"),
    MEDIUM("🟢 Medium"),
    LOW("🔵 Low")
}

fun determinePriority(endDate: String?): ReminderPriority {
    if (endDate == null) return ReminderPriority.LOW
    // Simple logic - can be enhanced based on actual date comparison
    return when {
        endDate.contains("2026-02-1") && endDate.contains("6") || endDate.contains("7") -> ReminderPriority.URGENT
        endDate.contains("2026-02") -> ReminderPriority.HIGH
        endDate.contains("2026-03") -> ReminderPriority.MEDIUM
        else -> ReminderPriority.LOW
    }
}

