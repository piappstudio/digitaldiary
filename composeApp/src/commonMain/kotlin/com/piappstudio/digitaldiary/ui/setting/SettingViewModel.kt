package com.piappstudio.digitaldiary.ui.setting

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

class SettingViewModel (val diaryRepository: DiaryRepository) : ViewModel() {
    init {
       Logger.d("Settings init $this")
    }
}