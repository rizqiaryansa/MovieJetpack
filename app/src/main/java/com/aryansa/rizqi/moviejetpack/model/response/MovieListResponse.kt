package com.aryansa.rizqi.moviejetpack.model.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("results")
    val results: List<Movie>
)

data class Movie(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null
) {
    fun getYearMovie() : String? = releaseDate?.split("-")?.get(0) ?: firstAirDate?.split("-")?.get(0)
}