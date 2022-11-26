package com.example.moviesapp.di

import com.example.moviesapp.util.FakeMovieService
import com.example.moviesapp.data.source.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {
    @Singleton
    @Provides
    fun provideMovieService(): MovieService {
        return FakeMovieService
    }
}