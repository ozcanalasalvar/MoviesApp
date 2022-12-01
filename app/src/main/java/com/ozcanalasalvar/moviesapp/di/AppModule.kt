package com.ozcanalasalvar.moviesapp.di

import com.ozcanalasalvar.moviesapp.data.source.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMovieService(): MovieService {
        return MovieService.create()
    }
}