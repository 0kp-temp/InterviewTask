package com.company.interviewtask

import androidx.recyclerview.widget.RecyclerView
import com.company.interviewtask.databinding.ItemSearchQueryBinding

class SearchQueryViewHolder(val binding: ItemSearchQueryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        data class SearchQueryUiData(val searchId: Long, val searchQuery: String)
}