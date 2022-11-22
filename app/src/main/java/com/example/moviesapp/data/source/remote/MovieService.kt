package com.example.moviesapp.data.source.remote

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.model.cast.CastingResponse
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.model.list.MovieResponse
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(QUERY_PAGE) page: Int,
    ): MovieResponse


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query(QUERY_PAGE) page: Int,
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: Int,
    ): MovieDetailDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(@Path("movie_id") movieId: Int): CastingResponse


    companion object {

        const val QUERY_API_KEY = "api_key"
        const val QUERY_PAGE = "page"
        const val QUERY_LANGUAGE = "language"
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): MovieService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        var request: Request = chain.request()
                        val url: HttpUrl =
                            request.url.newBuilder()
                                .addQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
                                .addQueryParameter(QUERY_LANGUAGE, "en-US")
                                .build()
                        request = request.newBuilder().url(url).build()
                        return chain.proceed(request)
                    }

                })
                .addInterceptor(logger)


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }


}