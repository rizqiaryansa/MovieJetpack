package com.aryansa.rizqi.moviejetpack.util

import androidx.paging.PagedList
import com.aryansa.rizqi.moviejetpack.model.entity.MovieEntity
import com.aryansa.rizqi.moviejetpack.model.response.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.model.response.MovieListResponse

sealed class ResultResponse {
    data class Success(val data: MovieListResponse?): ResultResponse()
    data class SuccessDetail(val data: MovieDetailResponse?): ResultResponse()
    data class SuccessFavorite(val data: PagedList<MovieEntity>): ResultResponse()
    data class Failure(val throwable: Throwable): ResultResponse()
}