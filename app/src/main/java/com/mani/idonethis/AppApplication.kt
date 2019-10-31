package com.mani.idonethis

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication  : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@AppApplication)
            // declare modules
            modules(AppInjector().viewModelModule)
        }
    }
}