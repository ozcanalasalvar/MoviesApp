package com.example.moviesapp.di

import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.MovieRepositoryImpl
import com.example.moviesapp.util.ConnectivityManagerNetworkMonitor
import com.example.moviesapp.util.NetworkMonitor
import com.example.moviesapp.util.TestNetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface TestDataModule {

    @Binds
    @Singleton
    fun bindRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    fun bindNetworkMonitor(networkMonitor: TestNetworkMonitor): NetworkMonitor

}