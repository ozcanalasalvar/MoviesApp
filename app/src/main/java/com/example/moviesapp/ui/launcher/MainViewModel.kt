package com.example.moviesapp.ui.launcher

import com.example.moviesapp.data.repository.Repository
import com.example.moviesapp.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository):BaseViewModel() {
}