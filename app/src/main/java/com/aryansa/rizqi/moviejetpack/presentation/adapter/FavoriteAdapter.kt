package com.aryansa.rizqi.moviejetpack.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.databinding.ItemFavoriteBinding
import com.aryansa.rizqi.moviejetpack.domain.model.local.MovieEntity
import com.aryansa.rizqi.moviejetpack.utils.AdapterDiffCallBack
import com.aryansa.rizqi.moviejetpack.presentation.viewholder.FavoriteViewHolder

class FavoriteAdapter(private val onClick: (MovieEntity) -> Unit):
    PagedListAdapter<MovieEntity, FavoriteViewHolder>(AdapterDiffCallBack.FavoriteDiffCallback) {

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFavoriteBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(binding, onClick)
    }
}