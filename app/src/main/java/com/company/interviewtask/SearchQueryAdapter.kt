package com.company.interviewtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.company.interviewtask.databinding.ItemSearchQueryBinding


class SearchQueryAdapter(private val onClick: (SearchQueryViewHolder.SearchQueryUiData) -> Unit) :
    ListAdapter<SearchQueryViewHolder.SearchQueryUiData, SearchQueryViewHolder>(object :
        DiffUtil.ItemCallback<SearchQueryViewHolder.SearchQueryUiData>() {
        override fun areItemsTheSame(
            old: SearchQueryViewHolder.SearchQueryUiData,
            new: SearchQueryViewHolder.SearchQueryUiData
        ): Boolean {
            return old.searchId == new.searchId
        }

        override fun areContentsTheSame(
            old: SearchQueryViewHolder.SearchQueryUiData,
            new: SearchQueryViewHolder.SearchQueryUiData
        ): Boolean {
            return old == new
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchQueryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchQueryViewHolder(ItemSearchQueryBinding.inflate(inflater, parent, false))
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