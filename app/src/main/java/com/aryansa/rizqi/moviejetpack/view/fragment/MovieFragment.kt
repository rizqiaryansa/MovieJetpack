package com.aryansa.rizqi.moviejetpack.view.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.adapter.MovieAdapter
import com.aryansa.rizqi.moviejetpack.adapter.ShimmerAdapter
import com.aryansa.rizqi.moviejetpack.databinding.MovieFragmentBinding
import com.aryansa.rizqi.moviejetpack.extension.observeData
import com.aryansa.rizqi.moviejetpack.extension.showToast
import com.aryansa.rizqi.moviejetpack.extension.startActivity
import com.aryansa.rizqi.moviejetpack.model.Movie
import com.aryansa.rizqi.moviejetpack.util.*
import com.aryansa.rizqi.moviejetpack.view.activity.DetailActivity
import com.aryansa.rizqi.moviejetpack.viewmodel.MovieViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MovieFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModelMovie: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    private val getScreenType: MovieType
        get() = (arguments?.getSerializable(getString(R.string.screen_type)) ?: MovieType.MOVIE) as MovieType

    private lateinit var binding: MovieFragmentBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModelMovie = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        EspressoIdlingResource.increment()
        viewModelMovie.getMovies(getScreenType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            viewModel = viewModelMovie
            lifecycleOwner = this@MovieFragment
            return root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData(viewModelMovie.state) {
            when (it) {
                is ResultResponse.Success -> {
                    movieAdapter.submitList(it.data?.results)
                }
                is ResultResponse.Failure -> {
                    context?.showToast(it.throwable.toString())
                }
            }
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter { onItemClick(it) }
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridItemDecoration(2, 25, true))
            adapter = movieAdapter
        }
        binding.rvShimmer.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridItemDecoration(2, 25, true))
            adapter = ShimmerAdapter()
        }
    }

    private fun onItemClick(movie: Movie) {
        context?.startActivity<DetailActivity> {
            putExtra(DetailActivity.TYPE_MOVIE, getScreenType)
            putExtra(DetailActivity.MOVIE_ID, movie.id)
        }
    }
}
