package com.aryansa.rizqi.moviejetpack.view.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
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
import com.aryansa.rizqi.moviejetpack.model.response.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.util.EspressoIdlingResource
import com.aryansa.rizqi.moviejetpack.util.FavoriteResponse
import com.aryansa.rizqi.moviejetpack.util.MovieType
import com.aryansa.rizqi.moviejetpack.util.ResultResponse
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

    private var movieDetail: MovieDetailResponse? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayout())

        viewModelDetailMovie = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailMovieViewModel::class.java)

        EspressoIdlingResource.increment()
        viewModelDetailMovie.getMovieDetail(getScreenType, idMovie)
        viewModelDetailMovie.checkIsFavorite(idMovie)

        initRecyclerView()

        observeViewModel()
        observeFavorite()
        observeIconFavorite()
        actionFavorite()
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
                    movieDetail = it.data
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

    private fun observeFavorite() {
        observeData(viewModelDetailMovie.stateFavorite) {
            when (it) {
                is FavoriteResponse.SaveFavorite -> {
                    showToast(getString(R.string.add_movie_favorites))
                }
                is FavoriteResponse.DeleteFavorite -> {
                    showToast(getString(R.string.removed_movie_favorites))
                }
            }
        }
        observeData(viewModelDetailMovie.isFavorite) {
            isFavorite = it
        }
    }

    private fun observeIconFavorite() {
        observeData(viewModelDetailMovie.iconFavorite) {
            binding.imgFavorite.setImageDrawable(ContextCompat.getDrawable(this, it))
        }
    }

    private fun initRecyclerView() {
        genreAdapter = GenreAdapter()
        binding.rvGenre.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }
    }

    private fun actionFavorite() {
        binding.imgFavorite.setOnClickListener {
            if (isFavorite) {
                movieDetail?.id?.let { it1 ->
                    viewModelDetailMovie.setDeleteFavorite(it1) }
            } else {
                movieDetail?.let { it1 ->
                    viewModelDetailMovie.setSaveFavorite(it1, getScreenType) }
            }
        }
    }
}
