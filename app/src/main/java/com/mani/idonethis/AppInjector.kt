package com.mani.idonethis

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mani.idonethis.ui.team.TeamViewModel
import com.mani.idonethis.ui.home.HomeViewModel
import com.mani.idonethis.ui.home.repository.TeamRepository
import com.mani.idonethis.ui.home.repository.ToDoRepository
import com.mani.idonethis.ui.home.repository.ToDoService
import com.mani.idonethis.ui.team.repository.TeamService
import com.mani.idonethis.ui.login.LoginViewModel
import com.mani.idonethis.ui.login.repository.UserApiService
import com.mani.idonethis.ui.login.repository.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppInjector {

    val viewModelModule = module {
        viewModel { TeamViewModel(get()) }
        viewModel { LoginViewModel(get(), get()) }
        viewModel { HomeViewModel(get(), get()) }
    }

    val repositoryModule = module {
        single { UserRepository(get()) }
        single { TeamRepository(get()) }
        single { ToDoRepository(get()) }
        single { UserSharedPreference(getSharedPreferences(androidApplication())) }

    }

    val apiServiceModule = module {
        single { RetrofitClient.provideRetrofit("https://idonethis-backend.herokuapp.com") }
        single { RetrofitClient.provideApiService(get(), UserApiService::class.java) }
        single { RetrofitClient.provideApiService(get(), TeamService::class.java) }
        single { RetrofitClient.provideApiService(get(), ToDoService::class.java) }
    }

    private fun getSharedPreferences(androidApplication: Application): SharedPreferences {
        return androidApplication.getSharedPreferences("default", Context.MODE_PRIVATE)
    }
}