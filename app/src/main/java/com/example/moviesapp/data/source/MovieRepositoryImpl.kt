package com.example.moviesapp.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviesapp.data.Movie
import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.data.source.remote.MovieService
import com.example.moviesapp.data.source.remote.NowPlayingMoviesPagingSource
import com.example.moviesapp.data.util.NETWORK_PAGE_SIZE
import com.example.moviesapp.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class MovieRepositoryImpl(private val service: MovieService) : MovieRepository {

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
            val result = service.getPopularMovies(0, "en-US").results.map {
                it.toMovie()
            }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}