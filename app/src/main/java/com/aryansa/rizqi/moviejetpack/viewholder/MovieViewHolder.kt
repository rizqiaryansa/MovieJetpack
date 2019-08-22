package com.aryansa.rizqi.moviejetpack.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aryansa.rizqi.moviejetpack.databinding.ItemMovieBinding
import com.aryansa.rizqi.moviejetpack.model.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    val onClick: (Movie) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie : Movie) {
        binding.apply {
            setMovie(movie)
            rootLayout.setOnClickListener {
                onClick(movie)
            }
            executePendingBindings()
        }
    }
}