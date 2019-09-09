package com.aryansa.rizqi.moviejetpack.model.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("genres")
    val genres: List<Genre>? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("name")
    val nameTv: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null
) {
    fun getYearMovie() : String? = releaseDate ?: firstAirDate
    fun getTitleMovie() : String? = originalTitle ?: nameTv
    fun getRatingVote() : Double? = voteAverage?.div(2)
}

data class Genre(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = null
)