package com.example.moviesapp.data.util

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse<T> (
    @Expose
    @SerializedName("results")
    val results: T,
    @Expose
    @SerializedName("page")
    val page: Int,
    @Expose
    @SerializedName("total_pages")
    val totalPages: Int,
    @Expose
    @SerializedName("total_results")
    val totalResults: Int,
)