package com.aryansa.rizqi.moviejetpack.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.base.BaseActivity
import com.aryansa.rizqi.moviejetpack.databinding.ActivityMainBinding
import com.aryansa.rizqi.moviejetpack.util.MovieType
import com.aryansa.rizqi.moviejetpack.view.fragment.MovieFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())

        val titleTab = listOf(getString(R.string.movie), getString(R.string.tv_show))
        val typeMovie = listOf(MovieType.MOVIE, MovieType.TV)
        view_pager2.adapter = createViewPagerAdapter(titleTab,
            typeMovie,
            supportFragmentManager,
            lifecycle)
        setTabLayout(titleTab)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    private fun setTabLayout(titleTab: List<String>) {
        TabLayoutMediator(tab_layout, view_pager2) { tab, position ->
            tab.text = titleTab[position]
        }.attach()
    }

    private fun createViewPagerAdapter(titleTab: List<String>,
                                       typeMovie: List<MovieType>,
                                       fm: FragmentManager,
                                       lifecycle: Lifecycle): RecyclerView.Adapter<*> {
        return object : FragmentStateAdapter(fm, lifecycle) {

            override fun getItemCount(): Int {
                return titleTab.size
            }

            override fun createFragment(position: Int): MovieFragment {
                return create(typeMovie[position])
            }
        }
    }

    private fun create(position: MovieType): MovieFragment {
        return MovieFragment().also {
            it.arguments = Bundle().apply {
                putSerializable(getString(R.string.screen_type), position)
            }
        }
    }
}
