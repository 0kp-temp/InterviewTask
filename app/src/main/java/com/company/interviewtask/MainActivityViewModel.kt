package com.company.interviewtask

import androidx.lifecycle.ViewModel
import com.company.interviewtask.database.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val appDatabase: AppDatabase) :
    ViewModel() {
}