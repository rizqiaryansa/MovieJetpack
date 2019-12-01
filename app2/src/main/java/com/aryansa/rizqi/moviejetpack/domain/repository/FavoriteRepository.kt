package com.aryansa.rizqi.moviejetpack.domain.repository

import androidx.paging.PagedList
import com.aryansa.rizqi.moviejetpack.domain.model.local.MovieEntity
import com.aryansa.rizqi.moviejetpack.utils.MovieType
import io.reactivex.Completable
import io.reactivex.Observable

interface FavoriteRepository {
    fun getFavoriteMovie(movieType: MovieType): Observable<PagedList<MovieEntity>>
    fun getCountMovie(id: Int): Observable<Int>
    fun saveFavoriteMovie(movieEntity: MovieEntity): Completable
    fun deleteFavoriteMovie(id: Int): Completable
}