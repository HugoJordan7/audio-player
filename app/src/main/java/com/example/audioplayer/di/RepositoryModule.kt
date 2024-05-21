package com.example.audioplayer.di

import com.example.audioplayer.service.repository.ExoPlayerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { providerExoPlayerRepository() }
}

private fun providerExoPlayerRepository() = ExoPlayerRepository()