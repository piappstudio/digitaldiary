package com.piappstudio.digitaldiary.di

import com.piappstudio.digitaldiary.JvmFileStorage
import com.piappstudio.digitaldiary.FileStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<FileStorage> { JvmFileStorage() }
}
