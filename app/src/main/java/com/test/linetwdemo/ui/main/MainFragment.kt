package com.test.linetwdemo.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.test.linetwdemo.R
import com.test.linetwdemo.model.Status
import com.test.linetwdemo.utils.RecyclerViewAdapter
import com.test.linetwdemo.utils.initLayout
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private val viewModel: MainViewModel by viewModel()
    private val movieAdapter = MovieRecyclerViewAdapter(ArrayList())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.initLayout(requireContext(), RecyclerView.VERTICAL)
        recyclerView.adapter = movieAdapter
        movieAdapter.recyclerViewItemClick = object :RecyclerViewAdapter.RecyclerViewItemClick{
            override fun onClick(position: Int) {
                closeKeyBoard()
                Navigation.findNavController(requireView())
                    .navigate(MainFragmentDirections.actionMainFragmentToMovieContentFragment(movieAdapter.getData()[position]))
            }
        }
        viewModel.result.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> movieAdapter.addAll(it.data?.data)
                Status.ERROR -> {
                    MaterialDialog(requireContext()).show {
                        title(text = getString(R.string.error))
                        message(text = it.message)
                        negativeButton(R.string.readLoad) { dialog ->
                            viewModel.getApi()
                        }
                        positiveButton(R.string.cancel)
                    }
                }
                Status.LOADING -> movieAdapter.clear()
            }
        }
        viewModel.getApi()
        editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    viewModel.getApi()
                } else {
                    viewModel.searchMovie("%".plus(s).plus("%"))
                }

            }
        })
    }
    private fun closeKeyBoard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}