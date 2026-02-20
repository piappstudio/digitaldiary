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
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime

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

fun calculateDaysRemaining(endDate: String?): Int? {
    if (endDate == null) return null
    return try {
        val end = LocalDate.parse(endDate)
        // Explicitly using kotlinx.datetime.Clock to avoid conflict with kotlin.time.Clock
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        now.daysUntil(end)
    } catch (e: Exception) {
        Logger.e(e) { "Error calculating days remaining: ${e.message}" }
        null
    }
}

fun determinePriority(endDate: String?): ReminderPriority {
    val daysRemaining = calculateDaysRemaining(endDate) ?: return ReminderPriority.LOW

    return when {
        daysRemaining < 0 -> ReminderPriority.LOW // Already passed
        daysRemaining <= 3 -> ReminderPriority.URGENT
        daysRemaining <= 7 -> ReminderPriority.HIGH
        daysRemaining <= 15 -> ReminderPriority.MEDIUM
        else -> ReminderPriority.LOW
    }
}
