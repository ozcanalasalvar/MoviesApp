package com.ozcanalasalvar.moviesapp.data.model.cast

import com.squareup.moshi.Json


data class CastingResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "cast") val cast: List<CastingDto>
)

data class CastingDto(
    @field:Json(name = "adult") val adult: Boolean,
    @field:Json(name = "cast_id") val cast_id: Int,
    @field:Json(name = "character") val character: String,
    @field:Json(name = "credit_id") val credit_id: String,
    @field:Json(name = "gender") val gender: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "known_for_department") val known_for_department: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "order") val order: Int,
    @field:Json(name = "original_name") val original_name: String?,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "profile_path") val profile_path: String?
)