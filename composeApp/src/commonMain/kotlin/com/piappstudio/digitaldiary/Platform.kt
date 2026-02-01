package com.piappstudio.digitaldiary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform