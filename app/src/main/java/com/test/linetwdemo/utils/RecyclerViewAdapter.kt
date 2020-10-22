package com.test.linetwdemo.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView



abstract class RecyclerViewAdapter<T, V : RecyclerViewItemViewHolder?>(
    private var data: MutableList<T>, @param:LayoutRes protected var rId: Int
) : RecyclerView.Adapter<V>() {
    interface RecyclerViewItemClick {
        fun onClick(position: Int)
    }

    var recyclerViewItemClick:RecyclerViewItemClick? = null


    fun getData(): List<T> {
        return data
    }

    fun addAll(d: List<T>?) {
        d?.let {
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        val view = LayoutInflater.from(parent.context).inflate(rId, parent, false)
        return initViewHolder(view)
    }

    abstract fun initViewHolder(view: View): V
    abstract fun initData(holder: V, positionData: T)
    protected fun getItemHeight(view: View, itemCount: Int): Int {
        val heightPixels = view.context.resources.displayMetrics.heightPixels
        return if (itemCount == 0) {
            0
        } else heightPixels / itemCount
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        recyclerViewItemClick?.let {
            holder?.let {view->
                view.itemView.setOnClickListener {_->
                    it.onClick(position)
                }
            }
        }
        initData(holder, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}