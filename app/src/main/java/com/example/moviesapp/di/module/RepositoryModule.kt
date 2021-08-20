package com.example.moviesapp.di.module

import com.example.moviesapp.data.repository.Repository
import com.example.moviesapp.data.repository.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ): Repository {
        return Repository(remoteDataSource)
    }
}