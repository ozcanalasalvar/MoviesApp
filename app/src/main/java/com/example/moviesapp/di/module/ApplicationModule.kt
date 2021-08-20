package com.example.moviesapp.di.module

import android.app.Application
import com.example.moviesapp.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: App) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }
}