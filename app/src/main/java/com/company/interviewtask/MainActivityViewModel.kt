package com.company.interviewtask

import androidx.lifecycle.ViewModel
import com.company.interviewtask.data.SearchEmployerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val searchEmployerRepository: SearchEmployerRepository
) : ViewModel() {

    sealed class AppError {
        object None : AppError()
        object SearchAlreadyDone : AppError()
        object SearchNetworkError : AppError()
    }

    var selectedSearchQuery: String = ""

    fun searchEmployer(query: String): Flow<AppError> = flow {
        selectedSearchQuery = query
        try {
            searchEmployerRepository.searchEmployer(query)
        } catch (e: SearchEmployerRepository.SearchAlreadyDoneException) {
            emit(AppError.SearchAlreadyDone)
        } catch (e: SearchEmployerRepository.SearchNetworkException) {
            emit(AppError.SearchNetworkError)
        }
        emit(AppError.None)
    }

}