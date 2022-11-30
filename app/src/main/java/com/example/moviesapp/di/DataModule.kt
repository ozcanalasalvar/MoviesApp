package com.example.moviesapp.di

import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.MovieRepositoryImpl
import com.example.moviesapp.util.ConnectivityManagerNetworkMonitor
import com.example.moviesapp.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    fun bindNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor
}