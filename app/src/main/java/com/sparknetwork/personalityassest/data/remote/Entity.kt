package com.sparknetwork.personalityassest.data.remote

import com.sparknetwork.personalityassest.data.model.DataResult

/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */
data class DataResponse(val status:Int?, val msg:String?, val data:DataResult?){
    fun isSuccess():Boolean= (status==200)
}