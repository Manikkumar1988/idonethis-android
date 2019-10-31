package com.mani.idonethis

import com.mani.idonethis.ui.gallery.GalleryViewModel
import com.mani.idonethis.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppInjector {

    val viewModelModule = module {
        viewModel { GalleryViewModel() }
        viewModel { LoginViewModel() }
    }

}