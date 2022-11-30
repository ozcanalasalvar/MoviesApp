package com.example.moviesapp.ui.launcher

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.ui.base.BaseActivity
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel =
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    override fun onInject() {
        super.onInject()
        getApplicationComponent().inject(this)
    }

}