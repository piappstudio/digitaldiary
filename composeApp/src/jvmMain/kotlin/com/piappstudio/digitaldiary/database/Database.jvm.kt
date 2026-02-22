package com.piappstudio.digitaldiary.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_1_2
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_2_3
import com.piappstudio.digitaldiary.database.DiaryRoomDatabase.Companion.MIGRATION_3_4
import java.io.File

actual fun getDatabaseBuilder(): RoomDatabase.Builder<DiaryRoomDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DiaryRoomDatabase.DB_NAME)
    return Room.databaseBuilder<DiaryRoomDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
     .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
}
