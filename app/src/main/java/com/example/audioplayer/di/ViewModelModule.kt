package com.example.audioplayer.di

import com.example.audioplayer.feature.main.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ providerMainViewModel() }
}

private fun providerMainViewModel() = MainViewModel()