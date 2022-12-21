package com.ozcanalasalvar.moviesapp.di

import com.ozcanalasalvar.moviesapp.domain.repository.MovieRepository
import com.ozcanalasalvar.moviesapp.data.MovieRepositoryImpl
import com.ozcanalasalvar.moviesapp.util.ConnectivityManagerNetworkMonitor
import com.ozcanalasalvar.moviesapp.util.NetworkMonitor
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