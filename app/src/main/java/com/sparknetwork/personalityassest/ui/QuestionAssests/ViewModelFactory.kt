package com.sparknetwork.personalityassest.ui.QuestionAssests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sparknetwork.personalityassest.data.remote.ResultDataSource

class ViewModelFactory(private val repository: ResultDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}