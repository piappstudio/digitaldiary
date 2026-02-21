package com.piappstudio.digitaldiary.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.DummyDataHelper
import com.piappstudio.digitaldiary.database.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class SplashViewModel(
    private val diaryRepository: DiaryRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    init {
        //insertDummyData()
    }

    /**
     * Insert comprehensive dummy data into the database
     * This is called only once on app startup
     * Comment this out if you don't want to insert dummy data
     */
    private fun insertDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dummyDataHelper = DummyDataHelper(diaryRepository, reminderRepository)
                dummyDataHelper.insertAllDummyData()
                Logger.d("SplashViewModel") { "Dummy data inserted successfully" }
            } catch (e: Exception) {
                Logger.e("SplashViewModel", e) { "Error inserting dummy data" }
            }

        }
    }
}