package com.ozcanalasalvar.moviesapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ozcanalasalvar.moviesapp.data.mapper.toMovie
import com.ozcanalasalvar.moviesapp.data.mapper.toMovieDetail
import com.ozcanalasalvar.moviesapp.data.model.cast.CastingResponse
import com.ozcanalasalvar.moviesapp.data.model.detail.MovieDetailDto
import com.ozcanalasalvar.moviesapp.data.source.remote.MovieService
import com.ozcanalasalvar.moviesapp.data.source.remote.NowPlayingMoviesPagingSource
import com.ozcanalasalvar.moviesapp.data.util.NETWORK_PAGE_SIZE
import com.ozcanalasalvar.moviesapp.data.util.Resource
import com.ozcanalasalvar.moviesapp.data.util.isSuccess
import com.ozcanalasalvar.moviesapp.di.IoDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService,
    @IoDispatcher val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override suspend fun getTrendMovies(): Resource<List<Movie>> {
        return try {
            val result = service.getPopularMovies(1).results.map {
                it.toMovie()
            }
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    override suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return combine(getDetail(movieId), getCasting(movieId)) { detail, castings ->
            when (detail) {
                is Resource.Error -> (Resource.Error(detail.exception))
                is Resource.Success -> {
                    if (castings.isSuccess) {
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
    }.flowOn(defaultDispatcher)

    private suspend fun getCasting(movieId: Int): Flow<Resource<CastingResponse>> = flow {
        try {
            val result = service.getMovieCast(movieId)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(defaultDispatcher)
}