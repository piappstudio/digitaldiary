package com.piappstudio.digitaldiary

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.writeToURL
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfFile
import platform.posix.memcpy

class IosFileStorage : FileStorage {
    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    override suspend fun saveImage(bytes: ByteArray, fileName: String): String? {
        val fileManager = NSFileManager.defaultManager
        val urls = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask)
        val documentDirectory = urls.first() as NSURL
        
        val fileURL = documentDirectory.URLByAppendingPathComponent(fileName)
        if (fileURL == null) return null

        // Ensure directory exists
        val directoryURL = fileURL.URLByDeletingLastPathComponent
        if (directoryURL != null && !fileManager.fileExistsAtPath(directoryURL.path!!)) {
            fileManager.createDirectoryAtURL(directoryURL, true, null, null)
        }

        val data = bytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
        }
        
        return if (data.writeToURL(fileURL, true)) {
            fileURL.path
        } else {
            null
        }
    }

    override fun getFilesDirectory(): String {
        val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
        return paths.first() as String
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    override suspend fun readBytes(uri: String): ByteArray? {
        val data = NSData.dataWithContentsOfFile(uri) ?: return null
        val bytes = ByteArray(data.length.toInt())
        bytes.usePinned { pinned ->
            memcpy(pinned.addressOf(0), data.bytes, data.length)
        }
        return bytes
    }
}

actual fun getFileStorage(): FileStorage = IosFileStorage()
