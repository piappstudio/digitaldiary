package com.piappstudio.digitaldiary.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.piappstudio.digitaldiary.database.DiaryRepository
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase
import com.piappstudio.digitaldiary.database.ReminderRepository
import com.piappstudio.digitaldiary.database.dao.ReminderDao
import com.piappstudio.digitaldiary.database.dao.UserEventDao
import com.piappstudio.digitaldiary.database.getDatabaseBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val databaseModule = module {
    single<DiaryRoomDatabase> {
        getDatabaseBuilder()
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single<UserEventDao> {
        get<DiaryRoomDatabase>().userEventDao()
    }
    single<ReminderDao> {
        get<DiaryRoomDatabase>().reminderDao()
    }

    single<DiaryRepository> {
        DiaryRepository(get())
    }
    single<ReminderRepository> {
        ReminderRepository(get())
    }
}

val commonModule = listOf(databaseModule, viewModelModules)
