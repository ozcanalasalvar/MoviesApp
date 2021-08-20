package com.example.moviesapp.di.component

import android.app.Application
import com.example.moviesapp.App
import com.example.moviesapp.di.module.ApplicationModule
import com.example.moviesapp.di.module.NetworkModule
import com.example.moviesapp.di.module.RepositoryModule
import com.example.moviesapp.di.module.ViewModelModule
import com.example.moviesapp.ui.detail.DetailFragment
import com.example.moviesapp.ui.launcher.MainActivity
import com.example.moviesapp.ui.main.MainScreenFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(application: App)

    fun inject(mainActivity: MainActivity)

    fun inject(detailFragment: DetailFragment)

    fun inject(mainScreenFragment: MainScreenFragment)

    fun application(): Application

}