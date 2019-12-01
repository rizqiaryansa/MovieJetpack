package com.aryansa.rizqi.moviejetpack.data.source.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aryansa.rizqi.moviejetpack.domain.model.local.MovieEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table WHERE typeMovie = :typeMovie")
    fun getMoviePaged(typeMovie: Int): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT COUNT(*) FROM movie_table WHERE idMovie = :id")
    fun getCountMovie(id: Int): Observable<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): Completable

    @Query("DELETE FROM movie_table WHERE idMovie = :id")
    fun deleteId(id: Int): Completable

}