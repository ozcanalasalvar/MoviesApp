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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        withContext(ioDispatcher) {
            coroutineScope {
                val _detail = async { getDetail(movieId) }
                val _cast = async { getCasting(movieId) }

                val detailResult = _detail.await()
                val castDtoResult = _cast.await()

                val casts = castDtoResult
                when (detailResult) {
                    is Resource.Error -> emit(Resource.Error(detailResult.exception))
                    is Resource.Success -> {
                        if (casts.isSuccess()) {
                            emit(Resource.Success(detailResult.data.toMovieDetail((casts as Resource.Success).data.cast)))
                        } else {
                            emit(Resource.Success(detailResult.data.toMovieDetail(null)))
                        }
                    }
                }
            }
        }
    }


    private suspend fun getDetail(movieId: Int): Resource<MovieDetailDto> {
        return try {
            val result = service.getMovieDetail(movieId)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private suspend fun getCasting(movieId: Int): Resource<CastingResponse> {
        return try {
            val result = service.getMovieCast(movieId)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}