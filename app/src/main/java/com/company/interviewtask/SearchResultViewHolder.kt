package com.company.interviewtask

import androidx.recyclerview.widget.RecyclerView
import com.company.interviewtask.databinding.ItemSearchResultBinding

class SearchResultViewHolder(val binding: ItemSearchResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    data class SearchResultUiData(val id: Long, val name: String)
}