package com.example.audioplayer.di

import com.example.audioplayer.feature.main.viewModel.MainViewModel
import com.example.audioplayer.service.repository.ExoPlayerRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ providerMainViewModel(get()) }
}

private fun providerMainViewModel(exoPlayerRepository: ExoPlayerRepository) = MainViewModel(exoPlayerRepository)