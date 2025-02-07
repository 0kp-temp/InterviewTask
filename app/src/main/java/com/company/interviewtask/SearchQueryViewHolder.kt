package com.company.interviewtask

import androidx.recyclerview.widget.RecyclerView
import com.company.interviewtask.databinding.ItemSearchQueryBinding

class SearchQueryViewHolder(val binding: ItemSearchQueryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    data class SearchResultUiData(val id: Long, val name: String)



}