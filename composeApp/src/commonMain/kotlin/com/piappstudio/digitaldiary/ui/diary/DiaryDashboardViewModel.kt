package com.piappstudio.digitaldiary.ui.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.entity.UserEvent
import com.piappstudio.digitaldiary.database.pojo.FilterOption
import com.piappstudio.digitaldiary.database.pojo.SortingOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiaryDashboardViewModel (val diaryRepository: DiaryRepository) : ViewModel() {
    private val _userEvents = MutableStateFlow<List<UserEvent>>(emptyList())
    val userEvents: StateFlow<List<UserEvent>> = _userEvents.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        Logger.d("DiaryDashboardViewModel init $this")
        loadEvents()
    }

    private fun loadEvents() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filterOption = FilterOption(
                    query = null,
                    sortingOrder = SortingOrder.NEW
                )
                diaryRepository.getAllUserEvents(filterOption).collect { userEvent ->
                    val currentList = _userEvents.value.toMutableList()
                    _userEvents.value = userEvent
                }
            } catch (e: Exception) {
                Logger.e("DiaryDashboardViewModel", e) { "Error loading events: ${e.message}" }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshEvents() {
        _userEvents.value = emptyList()
        loadEvents()
    }
}