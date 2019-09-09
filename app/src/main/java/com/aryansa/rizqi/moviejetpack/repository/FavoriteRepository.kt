package com.aryansa.rizqi.moviejetpack.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.aryansa.rizqi.moviejetpack.db.MovieDao
import com.aryansa.rizqi.moviejetpack.model.entity.MovieEntity
import com.aryansa.rizqi.moviejetpack.util.MovieType
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

interface FavoriteRepository {
    fun getFavoriteMovie(movieType: MovieType): Observable<PagedList<MovieEntity>>
    fun getCountMovie(id: Int): Observable<Int>
    fun saveFavoriteMovie(movieEntity: MovieEntity): Completable
    fun deleteFavoriteMovie(id: Int): Completable
}

class FavoriteRepositoryImpl @Inject
    constructor(private val dao: MovieDao): FavoriteRepository {

    override fun getFavoriteMovie(movieType: MovieType): Observable<PagedList<MovieEntity>> {
        return when(movieType) {
            MovieType.MOVIE -> RxPagedListBuilder(dao.getMoviePaged(0), 10).buildObservable()
            else -> RxPagedListBuilder(dao.getMoviePaged(1), 10).buildObservable()
        }
    }

    override fun getCountMovie(id: Int): Observable<Int> {
        return dao.getCountMovie(id)
    }

    override fun saveFavoriteMovie(movieEntity: MovieEntity): Completable {
        return dao.insertMovie(movieEntity)
    }

    override fun deleteFavoriteMovie(id: Int): Completable {
        return dao.deleteId(id)
    }
}