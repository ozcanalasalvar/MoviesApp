package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.di.component.AppComponent
import com.example.moviesapp.di.component.DaggerAppComponent
import com.example.moviesapp.di.module.ApplicationModule

class App : Application() {
    private lateinit var component: AppComponent

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        component.inject(this)
    }

    fun getAppComponent(): AppComponent {
        return component
    }
}