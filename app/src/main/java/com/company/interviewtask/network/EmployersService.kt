package com.company.interviewtask.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployersService {

    @GET("CBAEmployerservice.svc/rest/employers")
    suspend fun searchEmployers(
        @Query("filter") query: String,
        @Query("maxRows") maxRows: Int = 100
    ): Response<List<Employer>>
}