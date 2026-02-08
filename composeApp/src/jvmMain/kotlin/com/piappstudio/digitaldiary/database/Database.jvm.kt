package com.piappstudio.digitaldiary.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getDatabaseBuilder(): RoomDatabase.Builder<DiaryRoomDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DiaryRoomDatabase.DB_NAME)
    return Room.databaseBuilder<DiaryRoomDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
}