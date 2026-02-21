package com.piappstudio.digitaldiary

interface FileStorage {
    suspend fun saveImage(bytes: ByteArray, fileName: String): String?
    fun getFilesDirectory(): String
    suspend fun readBytes(uri: String): ByteArray?
}

expect fun getFileStorage(): FileStorage
