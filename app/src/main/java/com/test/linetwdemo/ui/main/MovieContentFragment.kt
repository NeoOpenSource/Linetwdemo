package com.test.linetwdemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.test.linetwdemo.R
import com.test.linetwdemo.decoder.Movie
import kotlinx.android.synthetic.main.fragment_movie_content.*


class MovieContentFragment : Fragment() {
    private var mParam: Movie = Movie("", "", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam = MovieContentFragmentArgs.fromBundle(it).param1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(mParam.thumb).centerCrop().into(imageViewContent)
        movieName.text = mParam.name
        totalViewsContent.text = mParam.total_views
        createDateContent.text = mParam.created_at
        ratingContent.text = mParam.rating
    }


}