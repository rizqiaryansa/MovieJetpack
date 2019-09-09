package com.aryansa.rizqi.moviejetpack.di.module

import com.aryansa.rizqi.moviejetpack.db.MovieDao
import com.aryansa.rizqi.moviejetpack.repository.FavoriteRepository
import com.aryansa.rizqi.moviejetpack.repository.FavoriteRepositoryImpl
import com.aryansa.rizqi.moviejetpack.repository.MovieRepository
import com.aryansa.rizqi.moviejetpack.repository.MovieRepositoryImpl
import com.aryansa.rizqi.moviejetpack.service.MovieService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(apiService: MovieService): MovieRepository = MovieRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: MovieDao): FavoriteRepository = FavoriteRepositoryImpl(dao)
}