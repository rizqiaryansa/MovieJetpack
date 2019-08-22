package com.aryansa.rizqi.moviejetpack.util

import androidx.recyclerview.widget.DiffUtil
import com.aryansa.rizqi.moviejetpack.model.Genre
import com.aryansa.rizqi.moviejetpack.model.Movie

class AdapterDiffCallBack {
    companion object {
        val MovieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }

        val GenreDiffCallback = object : DiffUtil.ItemCallback<Genre>() {
            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}