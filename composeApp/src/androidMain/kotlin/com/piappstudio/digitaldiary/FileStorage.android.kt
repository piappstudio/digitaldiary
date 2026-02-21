package com.piappstudio.digitaldiary

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AndroidFileStorage(private val context: Context) : FileStorage {
    override suspend fun saveImage(bytes: ByteArray, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.filesDir, fileName)
                file.parentFile?.let {
                    if (!it.exists()) it.mkdirs()
                }
                FileOutputStream(file).use { fos ->
                    fos.write(bytes)
                }
                file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun getFilesDirectory(): String {
        return context.filesDir.absolutePath
    }

    override suspend fun readBytes(uri: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                if (uri.startsWith("content://") || uri.startsWith("file://")) {
                    context.contentResolver.openInputStream(Uri.parse(uri))?.use { it.readBytes() }
                } else {
                    File(uri).readBytes()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

actual fun getFileStorage(): FileStorage {
    throw RuntimeException("Use Koin to inject FileStorage")
}
