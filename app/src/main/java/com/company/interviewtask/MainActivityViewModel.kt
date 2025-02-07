package com.company.interviewtask

import androidx.lifecycle.ViewModel
import com.company.interviewtask.data.SearchEmployerRepository
import com.company.interviewtask.database.DAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val searchEmployerRepository: SearchEmployerRepository,
    private val dao: DAO
) : ViewModel() {
}