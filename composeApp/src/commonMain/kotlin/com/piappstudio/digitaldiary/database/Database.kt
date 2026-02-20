package com.piappstudio.digitaldiary.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO



expect fun getDatabaseBuilder(): RoomDatabase.Builder<DiaryRoomDatabase>