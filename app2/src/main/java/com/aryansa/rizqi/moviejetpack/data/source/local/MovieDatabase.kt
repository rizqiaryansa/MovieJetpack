package com.aryansa.rizqi.moviejetpack.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aryansa.rizqi.moviejetpack.data.source.local.dao.MovieDao
import com.aryansa.rizqi.moviejetpack.domain.model.local.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}