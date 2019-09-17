package com.sparknetwork.personalityassest.di

import com.sparknetwork.personalityassest.data.remote.ResultDataSource
import com.sparknetwork.personalityassest.data.remote.MuseumRepository

object Injection {

    //MuseumRepository could be a singleton
    fun providerRepository(): ResultDataSource {
        return MuseumRepository()
    }
}