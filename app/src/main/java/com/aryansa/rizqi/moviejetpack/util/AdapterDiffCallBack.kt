package com.aryansa.rizqi.moviejetpack.util

import androidx.recyclerview.widget.DiffUtil
import com.aryansa.rizqi.moviejetpack.model.entity.MovieEntity
import com.aryansa.rizqi.moviejetpack.model.response.Genre
import com.aryansa.rizqi.moviejetpack.model.response.Movie

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

        val FavoriteDiffCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}