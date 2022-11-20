package com.example.moviesapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.data.mapper.toMovieDetail
import com.example.moviesapp.data.model.cast.CastingResponse
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.source.remote.MovieService
import com.example.moviesapp.data.source.remote.NowPlayingMoviesPagingSource
import com.example.moviesapp.data.util.NETWORK_PAGE_SIZE
import com.example.moviesapp.data.util.Resource
import com.example.moviesapp.data.util.isSuccess
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

class MovieRepositoryImpl(
    private val service: MovieService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NowPlayingMoviesPagingSource(service = service)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
    }

    override suspend fun getTrendMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            val result = service.getPopularMovies(1).results.map {
                it.toMovie()
            }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    override suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return combine(getDetail(550), getCasting(550)) { detail, castings ->
            when (detail) {
                is Resource.Error -> (Resource.Error(detail.exception))
                is Resource.Success -> {
                    if (castings.isSuccess()) {
                        Resource.Success(detail.data.toMovieDetail((castings as Resource.Success).data.cast))
                    } else {
                        Resource.Success(detail.data.toMovieDetail(null))
                    }
                }
            }
        }
    }

    private suspend fun getDetail(movieId: Int): Flow<Resource<MovieDetailDto>> = flow {
        try {
            val result = service.getMovieDetail(movieId)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    private suspend fun getCasting(movieId: Int): Flow<Resource<CastingResponse>> = flow {
        try {
            val result = service.getMovieCast(movieId)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}