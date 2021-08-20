package com.example.moviesapp.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.data.model.detail.MovieDetailModel
import com.example.moviesapp.data.repository.Repository
import com.example.moviesapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    var detailLiveData = MutableLiveData<MovieDetailModel>()
    var errorLiveData = MutableLiveData<String>()

    fun getMovieDetail(movieId: Int) {
        disposable.add(
            repository.getMovieDetail(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    detailLiveData.value = it
                }, {
                    errorLiveData.value = "Opps sth wrong bad."
                })
        )
    }
}