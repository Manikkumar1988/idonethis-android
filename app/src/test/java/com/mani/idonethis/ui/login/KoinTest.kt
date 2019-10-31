package com.mani.idonethis.ui.login

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

open class KoinTest {

    @Before
    fun initKoin() {
        startKoin { androidContext(ApplicationProvider.getApplicationContext<Application>()) }
    }

    @After
    fun destroyKoin() {
        stopKoin()
    }
}