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
import com.company.interviewtask.database.SearchQueryResults
import com.company.interviewtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAKE_FOR_HISTORY_BROWSING = 1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dao: DAO

    private val viewModel by viewModels<MainActivityViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchQueryAdapter: SearchQueryAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter

    private fun initializeSearchHistoryRecyclerView() {
        searchQueryAdapter = SearchQueryAdapter {
            viewModel.selectedSearchQuery = it.searchQuery
            binding.edittextMainactivitySearchquery.setText(it.searchQuery)
            lifecycleScope.launch {
                dao.getAllSearches().take(TAKE_FOR_HISTORY_BROWSING).collect { searches ->
                    displaySearchResults(searches, it.searchQuery)
                }
            }
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
        searchResultAdapter = SearchResultAdapter {
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

    private fun displaySearchQueries(searches: List<SearchQueryResults>) {
        searches.map { it.searchQuery }.let { searchHistory ->
            searchQueryAdapter.submitList(searchHistory.map {
                SearchQueryViewHolder.SearchQueryUiData(
                    it.searchId,
                    it.searchQuery
                )
            })
        }
    }

    private fun displaySearchResults(searches: List<SearchQueryResults>, selectedQuery: String) {
        searches.firstOrNull { it.searchQuery.searchQuery == selectedQuery }?.let {
            searchResultAdapter.submitList(it.results.map { result ->
                SearchResultViewHolder.SearchResultUiData(
                    result.id,
                    result.name
                )
            })
        } ?: searchResultAdapter.submitList(emptyList())
    }

    private fun onSearchPressed() {
        binding.imagebuttonMainactivitySearch.isEnabled = false
        val enteredQuery = binding.edittextMainactivitySearchquery.text.toString()
        lifecycleScope.launch {
            viewModel.searchEmployer(enteredQuery).collect {
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
        binding.buttonMainactivityClearsearchhistory.setOnClickListener {
            viewModel.onClearSearchHistoryPressed()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dao.getAllSearches().collect { searches ->
                    displaySearchQueries(searches)
                    displaySearchResults(searches, viewModel.selectedSearchQuery)
                    binding.imagebuttonMainactivitySearch.isEnabled = true
                }
            }
        }
        setupEdgeToEdge()
    }
}