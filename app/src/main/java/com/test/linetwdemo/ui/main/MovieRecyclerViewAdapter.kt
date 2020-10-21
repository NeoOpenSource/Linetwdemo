package com.test.linetwdemo.ui.main

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.test.linetwdemo.R
import com.test.linetwdemo.decoder.Movie
import com.test.linetwdemo.utils.RecyclerViewAdapter
import com.test.linetwdemo.utils.RecyclerViewItemViewHolder


class MovieRecyclerViewAdapter(data: List<Movie>) : RecyclerViewAdapter<Movie, MovieRecyclerViewAdapter.MovieViewHolder>(data.toMutableList(),R.layout.recycler_view_item){
    override fun initViewHolder(view: View): MovieViewHolder {
        return MovieViewHolder(view)
    }
    override fun initData(holder: MovieViewHolder, positionData: Movie) {
        holder.itemView.layoutParams.height = getItemHeight(holder.itemView, 9)
        //holder.imageView.setBackgroundResource(positionData.imageRid)
        //holder.name.text = positionData.name
    }

    class MovieViewHolder(itemView: View) : RecyclerViewItemViewHolder(itemView) {
        var imageView: AppCompatImageView = itemView.findViewById(R.id.imageView)
        //var name: AppCompatTextView = itemView.findViewById(R.id.textView)
    }
}