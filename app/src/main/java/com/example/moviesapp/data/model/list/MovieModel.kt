package com.example.moviesapp.data.model.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MovieModel(
    @Expose
    @SerializedName("adult")
    var adult: Boolean,
    @Expose
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @Expose
    @SerializedName("genre_ids")
    var genre_ids: List<Integer>,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("original_language")
    val original_language: String,
    @Expose
    @SerializedName("original_title")
    val original_title: String,
    @Expose
    @SerializedName("overview")
    val overview: String,
    @Expose
    @SerializedName("popularity")
    val popularity: Double,
    @Expose
    @SerializedName("poster_path")
    val poster_path: String,
    @Expose
    @SerializedName("release_date")
    val release_date: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("video")
    var video: Boolean,
    @Expose
    @SerializedName("vote_average")
    val vote_average: Double,
    @Expose
    @SerializedName("vote_count")
    var vote_count: Int
)

