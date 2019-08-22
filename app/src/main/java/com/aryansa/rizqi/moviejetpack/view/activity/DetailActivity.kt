package com.aryansa.rizqi.moviejetpack.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.adapter.GenreAdapter
import com.aryansa.rizqi.moviejetpack.base.BaseActivity
import com.aryansa.rizqi.moviejetpack.databinding.ActivityDetailBinding
import com.aryansa.rizqi.moviejetpack.extension.observeData
import com.aryansa.rizqi.moviejetpack.extension.showToast
import com.aryansa.rizqi.moviejetpack.util.*
import com.aryansa.rizqi.moviejetpack.viewmodel.DetailMovieViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class DetailActivity : BaseActivity() {

    companion object {
        const val TYPE_MOVIE = "TYPE_MOVIE"
        const val MOVIE_ID = "MOVIE_ID"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityDetailBinding

    private lateinit var viewModelDetailMovie: DetailMovieViewModel
    private lateinit var genreAdapter: GenreAdapter

    private val getScreenType: MovieType
        get() = (intent.getSerializableExtra(TYPE_MOVIE) ?: MovieType.MOVIE) as MovieType
    private val idMovie: Int
        get() = intent.getIntExtra(MOVIE_ID, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayout())

        viewModelDetailMovie = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailMovieViewModel::class.java)

        EspressoIdlingResource.increment()
        viewModelDetailMovie.getMovieDetail(getScreenType, idMovie)

        initRecyclerView()

        observeViewModel()
    }

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    private fun observeViewModel() {
        observeData(viewModelDetailMovie.state) {
            when (it) {
                is ResultResponse.SuccessDetail -> {
                    genreAdapter.submitList(it.data?.genres)
                    binding.detail = it.data
                }
                is ResultResponse.Failure -> {
                    showToast(it.throwable.toString())
                }
            }
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun initRecyclerView() {
        genreAdapter = GenreAdapter()
        binding.rvGenre.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }
    }
}
