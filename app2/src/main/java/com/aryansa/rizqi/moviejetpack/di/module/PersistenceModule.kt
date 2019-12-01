package com.aryansa.rizqi.moviejetpack.di.module

import android.app.Application
import androidx.room.Room
import com.aryansa.rizqi.moviejetpack.data.source.local.dao.MovieDao
import com.aryansa.rizqi.moviejetpack.data.source.local.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(application, MovieDatabase::class.java, "Movie.db")
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}