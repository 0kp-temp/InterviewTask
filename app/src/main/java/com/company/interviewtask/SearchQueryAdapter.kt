package com.company.interviewtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.company.interviewtask.database.SearchQuery
import com.company.interviewtask.databinding.ItemSearchQueryBinding


class SearchQueryAdapter(private val onClick: (SearchQuery) -> Unit) :
    ListAdapter<SearchQuery, SearchQueryViewHolder>(object :
        DiffUtil.ItemCallback<SearchQuery>() {
        override fun areItemsTheSame(
            old: SearchQuery, new: SearchQuery
        ): Boolean {
            return old.searchId == new.searchId
        }

        override fun areContentsTheSame(
            old: SearchQuery, new: SearchQuery
        ): Boolean {
            return old == new
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchQueryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchQueryViewHolder(ItemSearchQueryBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: SearchQueryViewHolder, position: Int) {
        getItem(position)?.let { query ->
            holder.binding.textviewSearchhistoryViewholderPastquery.apply {
                text = query.searchQuery
                setOnClickListener {
                    onClick(query)
                }
            }
        }
    }
}