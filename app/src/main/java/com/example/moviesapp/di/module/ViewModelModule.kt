package com.example.moviesapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.ui.detail.DetailViewModel
import com.example.moviesapp.ui.launcher.MainViewModel
import com.example.moviesapp.ui.main.MainScreenViewModel
import com.example.moviesapp.ui.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindsMainScreenViewModel(viewModel: MainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindsDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}