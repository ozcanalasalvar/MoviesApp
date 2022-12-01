package com.ozcanalasalvar.moviesapp.di

import com.ozcanalasalvar.moviesapp.data.MovieRepository
import com.ozcanalasalvar.moviesapp.data.MovieRepositoryImpl
import com.ozcanalasalvar.moviesapp.util.NetworkMonitor
import com.ozcanalasalvar.moviesapp.util.TestNetworkMonitor
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