package com.piappstudio.digitaldiary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class JvmFileStorage : FileStorage {
    override suspend fun saveImage(bytes: ByteArray, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val baseDir = File(System.getProperty("user.home"), ".digitaldiary")
                val file = File(baseDir, fileName)
                
                file.parentFile?.let {
                    if (!it.exists()) it.mkdirs()
                }
                
                FileOutputStream(file).use { it.write(bytes) }
                file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun getFilesDirectory(): String {
        return File(System.getProperty("user.home"), ".digitaldiary").absolutePath
    }

    override suspend fun readBytes(uri: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val file = if (uri.startsWith("file:")) {
                    File(URI(uri))
                } else {
                    File(uri)
                }
                if (file.exists()) {
                    file.readBytes()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

actual fun getFileStorage(): FileStorage = JvmFileStorage()
