package com.company.interviewtask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.interviewtask.database.DAO
import com.company.interviewtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dao: DAO

    private val viewModel by viewModels<MainActivityViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchQueryAdapter: SearchQueryAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter

    private fun initializeSearchHistoryRecyclerView() {
        searchQueryAdapter = SearchQueryAdapter() {
            viewModel.selectedSearchQuery = it.searchQuery
            binding.edittextMainactivitySearchquery.setText(it.searchQuery)
            refreshData()
        }
        binding.recyclerviewMainactivitySearchhistory.apply {
            adapter = searchQueryAdapter
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                stackFromEnd = true
                reverseLayout = true
            }
        }
    }

    private fun initializeSearchResultRecyclerView() {
        searchResultAdapter = SearchResultAdapter() {
            EmployerDetailsDialogFragment.newInstance(it.id)
                .show(supportFragmentManager, EmployerDetailsDialogFragment.TAG)
        }
        binding.recyclerviewMainactivitySearchresults.apply {
            adapter = searchResultAdapter
        }
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintlayoutMainactivityRootview) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun refreshData() {
        val currentSearchQuery = binding.edittextMainactivitySearchquery.text.toString()
        lifecycleScope.launch {
            dao.getAllSearches().take(1).collect { searches ->
                searches.map { it.searchQuery }.let { searchHistory ->
                    searchQueryAdapter.submitList(null)
                    searchQueryAdapter.submitList(searchHistory)
                }

                searches.firstOrNull { it.searchQuery.searchQuery == currentSearchQuery }
                    ?.let { searchQueryResults ->
                        searchResultAdapter.submitList(searchQueryResults.results.map {
                            SearchQueryViewHolder.SearchResultUiData(
                                it.id,
                                it.name
                            )
                        })
                    }
            }
        }
    }

    private fun onSearchPressed() {
        binding.imagebuttonMainactivitySearch.isEnabled = false
        val enteredQuery = binding.edittextMainactivitySearchquery.text.toString()
        lifecycleScope.launch {
            viewModel.searchEmployer(enteredQuery).take(1).collect {
                lifecycleScope.launch(Dispatchers.Main) {
                    when (it) {
                        MainActivityViewModel.AppError.None -> {
                            //no-op
                        }

                        MainActivityViewModel.AppError.SearchAlreadyDone -> {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.mainactivity_toast_search_already_done),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        MainActivityViewModel.AppError.SearchNetworkError -> {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.mainactivity_toast_network_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    binding.imagebuttonMainactivitySearch.isEnabled = true
                }
                refreshData()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeSearchHistoryRecyclerView()
        initializeSearchResultRecyclerView()
        binding.imagebuttonMainactivitySearch.setOnClickListener {
            onSearchPressed()
        }
        binding.edittextMainactivitySearchquery.setText(viewModel.selectedSearchQuery)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                refreshData()
            }
        }
        setupEdgeToEdge()
    }
}