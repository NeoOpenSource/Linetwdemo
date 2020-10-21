package com.test.linetwdemo.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.initLayout(context: Context, @RecyclerView.Orientation orientation:Int){
    val layoutManager = LinearLayoutManager(context)
    layoutManager.orientation = orientation
    this.layoutManager = layoutManager
}