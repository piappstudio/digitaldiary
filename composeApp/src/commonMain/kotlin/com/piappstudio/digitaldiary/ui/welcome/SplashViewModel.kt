package com.piappstudio.digitaldiary.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.entity.EventInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlin.time.Clock

class SplashViewModel (val diaryRepository: DiaryRepository) : ViewModel() {

    init {
        insertAndRead()
    }

    fun insertAndRead() {
        viewModelScope.launch (Dispatchers.IO) {

            val eventInfo = EventInfo().apply {
                title = "Test"
                description = "Test"
                emotion = "Test"
                dateInfo = Clock.System.now().toString()
            }
            val id = diaryRepository.insert(eventInfo)
            val userEvents = diaryRepository.getUserEvent(id)

            Logger.d("User Events: $userEvents.")

        }
    }
}