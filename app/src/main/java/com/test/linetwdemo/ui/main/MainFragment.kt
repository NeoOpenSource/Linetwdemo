package com.test.linetwdemo.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.linetwdemo.R
import com.test.linetwdemo.utils.initLayout
import kotlinx.android.synthetic.main.main_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by viewModel()
    private val movieAdapter = MovieRecyclerViewAdapter(ArrayList())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false).apply {
            message.setOnClickListener {
                viewModel.getApi()
            }
            recyclerView.initLayout(requireContext(),RecyclerView.VERTICAL)
            recyclerView.adapter = movieAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }

}