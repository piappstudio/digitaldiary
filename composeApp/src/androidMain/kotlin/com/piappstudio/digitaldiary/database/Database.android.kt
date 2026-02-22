package com.piappstudio.digitaldiary.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_1_2
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_2_3
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_3_4
import org.koin.mp.KoinPlatform.getKoin

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<DiaryRoomDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DiaryRoomDatabase.DB_NAME)
    return Room.databaseBuilder<DiaryRoomDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<DiaryRoomDatabase> {
    val context: Context = getKoin().get()
    return getDatabaseBuilder(context)
}
