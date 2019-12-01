package com.aryansa.rizqi.moviejetpack.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aryansa.rizqi.moviejetpack.databinding.ItemGenreBinding
import com.aryansa.rizqi.moviejetpack.domain.model.remote.Genre

class GenreViewHolder(
    private val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(genre : Genre) {
        binding.apply {
            setGenre(genre)
            executePendingBindings()
        }
    }
}