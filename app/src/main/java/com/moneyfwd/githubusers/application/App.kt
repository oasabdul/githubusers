package com.moneyfwd.githubusers.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        initializeKoinModules()
        super.onCreate()
    }

    private fun initializeKoinModules() {
        startKoin {
            androidContext(this@App)
            modules(KoinGraph.allModules())
        }
    }
}