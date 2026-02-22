package com.piappstudio.digitaldiary.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_info")
data class MediaInfo(
    @PrimaryKey(autoGenerate = true) var mediaId: Long? = null,
    val mediaPath: String,
    var eventKey: Long
)