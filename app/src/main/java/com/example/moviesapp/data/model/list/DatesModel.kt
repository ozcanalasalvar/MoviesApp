package com.example.moviesapp.data.model.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DatesModel(
    @Expose
    @SerializedName("maximum")
    var maximum: String,
    @Expose
    @SerializedName("minimum")
    var minimum: String
)