package com.robo.window.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.robo.window.data.ble.BleConnection
import com.robo.window.R
import com.robo.window.Singleton
import com.robo.window.ui.adapter.SearchCardAdapter
import com.robo.window.ui.viewmodel.SearchFragmentViewModel

class SearchFragment : Fragment() {

    private lateinit var searchView: View

    companion object {
        lateinit var viewModel: SearchFragmentViewModel
    }

    private lateinit var swipeRefresher: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchView = inflater.inflate(R.layout.search_fragment, container, false)
        viewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        initView()
        setObservers()
        setListeners()
        return searchView
    }

    private fun initView() {
        swipeRefresher = searchView.findViewById(R.id.refresh_list_device)
        recyclerView = searchView.findViewById(R.id.recycler_view_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SearchCardAdapter(listOf())
        progressBar = searchView.findViewById(R.id.connect_progress)
        progressBar.visibility = View.GONE
    }

    private fun setListeners() {
        swipeRefresher.setOnRefreshListener {
            viewModel.searchDevice()
        }
    }

    private fun setObservers() {
        viewModel.searchState.observe(viewLifecycleOwner) {
            swipeRefresher.isRefreshing = it
        }
        viewModel.deviceList.observe(viewLifecycleOwner) {
            recyclerView.adapter = SearchCardAdapter(it)
        }
        viewModel.deviceState.observe(viewLifecycleOwner) {
            Log.e("DS", it.toString())
            when (it) {
                BleConnection.CONNECTING -> progressBar.visibility = View.VISIBLE
                BleConnection.CONNECTED -> {
                    progressBar.visibility = View.GONE
                    Singleton.showFragment(BaseFragment(), "base")
                    Singleton.fragmentManager.beginTransaction().remove(this).commit()
                }
                else -> progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel
    }
}
