package com.aryansa.rizqi.moviejetpack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.databinding.ItemMovieBinding
import com.aryansa.rizqi.moviejetpack.model.Movie
import com.aryansa.rizqi.moviejetpack.util.AdapterDiffCallBack
import com.aryansa.rizqi.moviejetpack.viewholder.MovieViewHolder

class MovieAdapter(private val onClick: (Movie) -> Unit):
    ListAdapter<Movie, MovieViewHolder>(AdapterDiffCallBack.MovieDiffCallback) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMovieBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_movie, parent, false)
        return MovieViewHolder(binding, onClick)
    }
}