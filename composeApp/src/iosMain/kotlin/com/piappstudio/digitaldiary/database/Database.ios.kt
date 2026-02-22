package com.piappstudio.digitaldiary.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.NativeSQLiteDriver
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_1_2
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_2_3
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_3_4
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseBuilder(): RoomDatabase.Builder<DiaryRoomDatabase> {
    val dbFilePath = documentDirectory() + "/${DiaryRoomDatabase.DB_NAME}"
    return Room.databaseBuilder<DiaryRoomDatabase>(
        name = dbFilePath,
    ).setDriver(NativeSQLiteDriver())
     .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
