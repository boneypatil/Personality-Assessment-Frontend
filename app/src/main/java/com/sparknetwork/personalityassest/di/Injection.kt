package com.sparknetwork.personalityassest.di

import com.sparknetwork.personalityassest.data.remote.ResultDataSource
import com.sparknetwork.personalityassest.data.remote.ResultRepository

object Injection {

    //ResultRepository could be a singleton
    fun providerRepository(): ResultDataSource {
        return ResultRepository()
    }
}