package com.sparknetwork.personalityassest.data.remote

import com.sparknetwork.personalityassest.data.model.DataResult

data class DataResponse(val status:Int?, val msg:String?, val data:DataResult?){
    fun isSuccess():Boolean= (status==200)
}