package com.cse_411_project.aigy

import android.app.Application
import com.cse_411_project.aigy.core.allFeatures
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Dependency Injection Initialization via Koin.
         *
         * @see [https://insert-koin.io/docs/setup/koin]
         * @see [https://insert-koin.io/docs/reference/koin-android/start/]
         */
        startKoin {
            androidContext(this@AndroidApplication)
            androidLogger()
            modules(allFeatures().map { it.diModule() })
        }
    }
}
