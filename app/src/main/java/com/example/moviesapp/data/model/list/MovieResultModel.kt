package com.example.moviesapp.data.model.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResultModel(
    @Expose
    @SerializedName("dates")
    val dates: DatesModel,
    @Expose
    @SerializedName("results")
    val results: List<MovieModel>,
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