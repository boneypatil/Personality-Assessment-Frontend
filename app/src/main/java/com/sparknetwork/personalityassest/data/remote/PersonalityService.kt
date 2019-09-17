package com.sparknetwork.personalityassest.data.remote



import com.sparknetwork.personalityassest.data.model.DataResult
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */
interface PersonalityService {
    @GET("category/questions")
    fun getData(): Call<DataResult>
}