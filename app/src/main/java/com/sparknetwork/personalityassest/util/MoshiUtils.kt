package com.sparknetwork.personalityassest.util

import com.sparknetwork.personalityassest.data.model.DataResult
import com.squareup.moshi.Moshi

/**
 * Created by rahul.p
 * on 9/13/2019
 * as project Personality Assesstment
 *
 */
class MoshiUtils{
    private val moshi = Moshi.Builder().build()

    fun deserializeDataResult(dataResult: String?): DataResult? {
        return try {
            val adapter = moshi.adapter(DataResult::class.java)
            val parsedError = adapter.fromJson(dataResult)
            parsedError
        } catch (e: Exception) {
            null
        }
    }
}