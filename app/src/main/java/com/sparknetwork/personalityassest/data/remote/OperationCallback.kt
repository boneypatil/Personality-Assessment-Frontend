package com.sparknetwork.personalityassest.data.remote

interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}