package com.piappstudio.digitaldiary.database

import androidx.room.RoomDatabaseConstructor

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<DiaryRoomDatabase> {
    override fun initialize(): DiaryRoomDatabase
}