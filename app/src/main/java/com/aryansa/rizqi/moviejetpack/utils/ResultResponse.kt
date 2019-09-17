package com.aryansa.rizqi.moviejetpack.utils

import androidx.paging.PagedList
import com.aryansa.rizqi.moviejetpack.domain.model.local.MovieEntity
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieListResponse

sealed class ResultResponse {
    data class Success(val data: MovieListResponse?): ResultResponse()
    data class SuccessDetail(val data: MovieDetailResponse?): ResultResponse()
    data class SuccessFavorite(val data: PagedList<MovieEntity>): ResultResponse()
    data class Failure(val throwable: Throwable): ResultResponse()
}