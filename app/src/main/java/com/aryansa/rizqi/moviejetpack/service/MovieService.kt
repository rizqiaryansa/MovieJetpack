package com.aryansa.rizqi.moviejetpack.service

import com.aryansa.rizqi.moviejetpack.model.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.model.MovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    fun getMovie(
        @Query("api_key") api_key : String
    ): Single<MovieListResponse>

    @GET("discover/tv")
    fun getTvShow(
        @Query("api_key") api_key : String
    ): Single<MovieListResponse>

    @GET("movie/{idMovie}")
    fun getMovieDetail(
        @Path("idMovie") idMovie: Int,
        @Query("api_key") api_key : String
    ): Single<MovieDetailResponse>

    @GET("tv/{idTv}")
    fun getTvDetail(
        @Path("idTv") idMovie: Int,
        @Query("api_key") api_key : String
    ): Single<MovieDetailResponse>

}