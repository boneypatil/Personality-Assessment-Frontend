package com.sparknetwork.personalityassest.data.remote

import android.util.Log
import com.sparknetwork.personalityassest.BuildConfig
import com.sparknetwork.personalityassest.data.model.DataResult
import com.sparknetwork.personalityassest.util.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */
const val TAG="CONSOLE"


class ResultRepository: ResultDataSource {

    private var call:Call<DataResult>?=null
    private val retrofit = ApiUtils.getClosedRetrofit(BuildConfig.COMMON_URL)
    private val apiClient = retrofit.create(PersonalityService::class.java)

    override fun retrieveTestData(callback: OperationCallback) {
        call=apiClient.getData()
        call?.enqueue(object :Callback<DataResult>{
            override fun onFailure(call: Call<DataResult>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
                response?.body()?.let {
                    if(response.isSuccessful && (it!=null)){
                        Log.v(TAG, "data ${it.questions}")
                        callback.onSuccess(it)
                    }else{
                        callback.onError("")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}

interface ResultDataSource {

    fun retrieveTestData(callback: OperationCallback)
    fun cancel()
}