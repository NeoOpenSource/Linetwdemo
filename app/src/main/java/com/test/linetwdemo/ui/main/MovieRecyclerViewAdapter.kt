package com.test.linetwdemo.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.test.linetwdemo.R
import com.test.linetwdemo.decoder.Movie
import com.test.linetwdemo.utils.RecyclerViewAdapter
import com.test.linetwdemo.utils.RecyclerViewItemViewHolder
import kotlinx.android.synthetic.main.recycler_view_item.view.*


class MovieRecyclerViewAdapter(data: List<Movie>) :
    RecyclerViewAdapter<Movie, MovieRecyclerViewAdapter.MovieViewHolder>(
        data.toMutableList(),
        R.layout.recycler_view_item
    ) {
    override fun initViewHolder(view: View): MovieViewHolder {
        return MovieViewHolder(view)
    }

    override fun initData(holder: MovieViewHolder, positionData: Movie) {
        holder.itemView.layoutParams.height = getItemHeight(holder.itemView, 4)
        Glide.with(holder.itemView).load(positionData.thumb).centerCrop().into(holder.itemView.imageView)
        holder.itemView.nameTextContent.text = positionData.name
        holder.itemView.createDateContent.text = positionData.created_at
        holder.itemView.ratingTextContent.text = positionData.rating
        //holder.imageView.setBackgroundResource(positionData.imageRid)
        //holder.name.text = positionData.name
    }

    class MovieViewHolder(itemView: View) : RecyclerViewItemViewHolder(itemView) {}
}