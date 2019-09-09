package com.aryansa.rizqi.moviejetpack.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.base.BaseActivity
import com.aryansa.rizqi.moviejetpack.databinding.ActivityFavoriteBinding
import com.aryansa.rizqi.moviejetpack.extension.setTabLayout
import com.aryansa.rizqi.moviejetpack.util.MovieType
import com.aryansa.rizqi.moviejetpack.view.fragment.FavoriteFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_favorite.*
import javax.inject.Inject

class FavoriteActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())

        setSupportActionBar(toolbarFavorite)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        }

        val titleTab = listOf(getString(R.string.movie), getString(R.string.tv_show))
        val typeMovie = listOf(MovieType.MOVIE, MovieType.TV)
        viewPager2Favorite.adapter = createViewPagerAdapter(titleTab,
            typeMovie,
            supportFragmentManager,
            lifecycle)
        setTabLayout(titleTab, tab_layoutFavorite, viewPager2Favorite)
    }

    override fun getLayout(): Int {
        return R.layout.activity_favorite
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    private fun createViewPagerAdapter(titleTab: List<String>,
                                       typeMovie: List<MovieType>,
                                       fm: FragmentManager,
                                       lifecycle: Lifecycle
    ): RecyclerView.Adapter<*> {
        return object : FragmentStateAdapter(fm, lifecycle) {

            override fun getItemCount(): Int {
                return titleTab.size
            }

            override fun createFragment(position: Int): FavoriteFragment {
                return create(typeMovie[position])
            }
        }
    }

    fun create(position: MovieType): FavoriteFragment {
        return FavoriteFragment().also {
            it.arguments = Bundle().apply {
                putSerializable(getString(R.string.screen_type), position)
            }
        }
    }
}
