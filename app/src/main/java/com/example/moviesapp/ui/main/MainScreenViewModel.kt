package com.example.moviesapp.ui.main

import com.example.moviesapp.data.repository.Repository
import com.example.moviesapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    var listener: MainScreenListener? = null

    var loadMore = false

    fun getMovies() {
        getUpComingVideos(1, true)
        getNowPlaying()
    }

    fun getUpComingVideos(page: Int, isRefresh: Boolean) {

        if (isRefresh) {
            listener?.resetScrollSate()
            listener?.showLoading()
        }

        disposable.add(
            repository.getUpComingMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val maxPage = it.totalPages
                    val movies = it.results
                    if (isRefresh) {
                        listener?.updateListContent(movies)
                    } else {
                        listener?.insertListContent(movies)
                    }

                    loadMore = page <= maxPage
                    listener?.hideLoading()

                }, {
                    listener?.onError("Opps sth wrong bad.")
                    listener?.hideLoading()
                })
        )

    }


    private fun getNowPlaying() {
        disposable.add(
            repository.getNowPlayingMovies("6cea5507c1dafd8f492d1d9a552483bc", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.results?.let { movies ->
                        listener?.updateSliderContent(movies)
                    }
                }, {
                    listener?.onError("Opps sth wrong bad.")
                })
        )
    }
}