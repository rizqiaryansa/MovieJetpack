package com.aryansa.rizqi.moviejetpack.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryansa.rizqi.moviejetpack.R

class ShimmerAdapter : RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ShimmerViewHolder {
        return ShimmerViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_shimmer, viewGroup, false))
    }

    override fun onBindViewHolder(newsViewHolder: ShimmerViewHolder, i: Int) {}

    override fun getItemCount(): Int = 4

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}