package com.company.interviewtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.company.interviewtask.databinding.ItemSearchResultBinding

class SearchResultAdapter(private val onClick: (SearchResultViewHolder.SearchResultUiData) -> Unit) :
    ListAdapter<SearchResultViewHolder.SearchResultUiData, SearchResultViewHolder>(
        object : DiffUtil.ItemCallback<SearchResultViewHolder.SearchResultUiData>() {
            override fun areItemsTheSame(
                old: SearchResultViewHolder.SearchResultUiData,
                new: SearchResultViewHolder.SearchResultUiData
            ): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(
                old: SearchResultViewHolder.SearchResultUiData,
                new: SearchResultViewHolder.SearchResultUiData
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
        getItem(position)?.let { data ->
            holder.binding.buttonSearchresultViewholderResult.text = data.name
            holder.binding.buttonSearchresultViewholderResult.setOnClickListener {
                onClick(data)
            }
        }
    }
}