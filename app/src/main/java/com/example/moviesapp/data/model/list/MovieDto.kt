package com.example.moviesapp.data.model.list

import com.google.gson.annotations.SerializedName


data class MovieDto(
    @SerializedName("id") var id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("original_title") val original_title: String?,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("video") var video: Boolean,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") var vote_count: Int,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("backdrop_path") val backdrop_path: String?,
)

