package com.company.interviewtask.di

import android.content.Context
import androidx.room.Room
import com.company.interviewtask.database.AppDatabase
import com.company.interviewtask.database.DAO
import com.company.interviewtask.network.EmployersService
import com.company.interviewtask.network.EndpointServiceCreator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "app-database"

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideEmployersService(): EmployersService =
        EndpointServiceCreator.createEmployersService()

    @Provides
    @Singleton
    fun provideDAO(applicationDatabase: AppDatabase): DAO = applicationDatabase.DAO()

}