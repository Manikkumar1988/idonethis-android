package com.mani.idonethis

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mani.idonethis.ui.gallery.GalleryViewModel
import com.mani.idonethis.ui.login.LoginViewModel
import com.mani.idonethis.ui.login.repository.UserApiService
import com.mani.idonethis.ui.login.repository.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppInjector {

    val viewModelModule = module {
        viewModel { GalleryViewModel() }
        viewModel { LoginViewModel(get(), get()) }
    }

    val repositoryModule = module {
        single { UserRepository(get()) }
        single { getSharedPreferences(androidApplication()) }

    }

    val apiServiceModule = module {
        single { RetrofitClient.provideRetrofit("https://idonethis-backend.herokuapp.com") }
        single { RetrofitClient.provideApiService(get(), UserApiService::class.java) }
    }

    private fun getSharedPreferences(androidApplication: Application): SharedPreferences {
        return androidApplication.getSharedPreferences("default", Context.MODE_PRIVATE)
    }
}