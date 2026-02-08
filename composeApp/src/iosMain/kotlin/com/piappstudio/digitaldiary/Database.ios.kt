package com.piappstudio.digitaldiary

import androidx.room.Room
import androidx.room.RoomDatabase
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<DiaryRoomDatabase> {
    val dbFilePath = documentDirectory() + "/${DiaryRoomDatabase.DB_NAME}"
    return Room.databaseBuilder<DiaryRoomDatabase>(
        name = dbFilePath,
    )
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