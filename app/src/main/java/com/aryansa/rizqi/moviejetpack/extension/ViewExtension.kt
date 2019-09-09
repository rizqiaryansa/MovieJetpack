package com.aryansa.rizqi.moviejetpack.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun setTabLayout(titleTab: List<String>, tabLayout: TabLayout, view_pager2: ViewPager2) {
    TabLayoutMediator(tabLayout, view_pager2) { tab, position ->
        tab.text = titleTab[position]
    }.attach()
}