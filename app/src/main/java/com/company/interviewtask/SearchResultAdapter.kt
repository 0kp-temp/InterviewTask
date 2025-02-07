package com.company.interviewtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.company.interviewtask.databinding.ItemSearchResultBinding

class SearchResultAdapter :
    ListAdapter<SearchQueryViewHolder.SearchResultUiData, SearchResultViewHolder>(
        object : DiffUtil.ItemCallback<SearchQueryViewHolder.SearchResultUiData>() {
            override fun areItemsTheSame(
                old: SearchQueryViewHolder.SearchResultUiData,
                new: SearchQueryViewHolder.SearchResultUiData
            ): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(
                old: SearchQueryViewHolder.SearchResultUiData,
                new: SearchQueryViewHolder.SearchResultUiData
            ): Boolean {
                return old == new
            }
        }
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchResultViewHolder(ItemSearchResultBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.buttonSearchresultViewholderResult.text = it.name
        }
    }
}