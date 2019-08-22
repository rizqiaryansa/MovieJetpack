package com.aryansa.rizqi.moviejetpack.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aryansa.rizqi.moviejetpack.databinding.ItemGenreBinding
import com.aryansa.rizqi.moviejetpack.model.Genre

class GenreViewHolder(
    private val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(genre : Genre) {
        binding.apply {
            setGenre(genre)
            executePendingBindings()
        }
    }
}