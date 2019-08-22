package com.aryansa.rizqi.moviejetpack.di.component

import com.aryansa.rizqi.moviejetpack.di.module.FragmentModule
import com.aryansa.rizqi.moviejetpack.view.activity.DetailActivity
import com.aryansa.rizqi.moviejetpack.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity
}