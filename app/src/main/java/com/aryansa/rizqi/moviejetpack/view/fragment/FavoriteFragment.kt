package com.aryansa.rizqi.moviejetpack.view.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.adapter.FavoriteAdapter
import com.aryansa.rizqi.moviejetpack.adapter.ShimmerAdapter
import com.aryansa.rizqi.moviejetpack.databinding.FragmentFavoriteBinding
import com.aryansa.rizqi.moviejetpack.extension.observeData
import com.aryansa.rizqi.moviejetpack.extension.startActivity
import com.aryansa.rizqi.moviejetpack.model.entity.MovieEntity
import com.aryansa.rizqi.moviejetpack.util.EspressoIdlingResource
import com.aryansa.rizqi.moviejetpack.util.GridItemDecoration
import com.aryansa.rizqi.moviejetpack.util.MovieType
import com.aryansa.rizqi.moviejetpack.util.ResultResponse
import com.aryansa.rizqi.moviejetpack.view.activity.DetailActivity
import com.aryansa.rizqi.moviejetpack.viewmodel.FavoriteViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModelFavorite: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val getScreenType: MovieType
        get() = (arguments?.getSerializable(getString(R.string.screen_type)) ?: MovieType.MOVIE) as MovieType

    private lateinit var binding: FragmentFavoriteBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModelFavorite = ViewModelProviders.of(this, viewModelFactory)
            .get(FavoriteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        EspressoIdlingResource.increment()
        viewModelFavorite.getFavoriteMovie(getScreenType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        with(binding) {
            viewModel = viewModelFavorite
            lifecycleOwner = this@FavoriteFragment
            return root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData(viewModelFavorite.state) {
            when (it) {
                is ResultResponse.SuccessFavorite -> {
                    favoriteAdapter.submitList(it.data)
                }
            }
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = FavoriteAdapter { onItemClick(it) }

        binding.rvFavorite.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridItemDecoration(2, 25, true))
            adapter = favoriteAdapter
        }
        binding.rvShimmerFavorite.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridItemDecoration(2, 25, true))
            adapter = ShimmerAdapter()
        }
    }

    private fun onItemClick(movie: MovieEntity) {
        context?.startActivity<DetailActivity> {
            putExtra(DetailActivity.TYPE_MOVIE, getScreenType)
            putExtra(DetailActivity.MOVIE_ID, movie.idMovie)
        }
    }

}
