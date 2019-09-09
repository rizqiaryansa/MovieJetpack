package com.aryansa.rizqi.moviejetpack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.databinding.ItemGenreBinding
import com.aryansa.rizqi.moviejetpack.model.response.Genre
import com.aryansa.rizqi.moviejetpack.util.AdapterDiffCallBack
import com.aryansa.rizqi.moviejetpack.viewholder.GenreViewHolder

class GenreAdapter:
    ListAdapter<Genre, GenreViewHolder>(AdapterDiffCallBack.GenreDiffCallback) {

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemGenreBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_genre, parent, false)
        return GenreViewHolder(binding)
    }
}