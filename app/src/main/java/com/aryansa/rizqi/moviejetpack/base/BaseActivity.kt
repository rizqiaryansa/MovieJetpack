package com.aryansa.rizqi.moviejetpack.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun getLayout() : Int
}