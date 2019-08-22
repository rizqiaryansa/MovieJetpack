package com.aryansa.rizqi.moviejetpack.di.module

import com.aryansa.rizqi.moviejetpack.view.fragment.MovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMovieFragment(): MovieFragment
}