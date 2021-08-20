package com.example.moviesapp.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel() : ViewModel(), CoroutineScope {

    private val job = Job()

    protected lateinit var apiKey: String

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val disposable = CompositeDisposable()

    fun setServiceKey(key: String) {
        this.apiKey = key
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        job.cancel()
    }
}