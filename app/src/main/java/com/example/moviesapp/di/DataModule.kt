package com.example.moviesapp.di

import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.source.MovieRepositoryImpl
import com.example.moviesapp.data.source.remote.MovieService
import com.example.moviesapp.util.ConnectivityManagerNetworkMonitor
import com.example.moviesapp.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor
}