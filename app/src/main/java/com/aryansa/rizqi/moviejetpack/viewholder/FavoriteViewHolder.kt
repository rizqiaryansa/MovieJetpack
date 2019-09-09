package com.aryansa.rizqi.moviejetpack.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aryansa.rizqi.moviejetpack.databinding.ItemFavoriteBinding
import com.aryansa.rizqi.moviejetpack.model.entity.MovieEntity

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding,
    val onClick: (MovieEntity) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie : MovieEntity) {
        binding.apply {
            setMovie(movie)
            rootLayout.setOnClickListener {
                onClick(movie)
            }
            executePendingBindings()
        }
    }
}