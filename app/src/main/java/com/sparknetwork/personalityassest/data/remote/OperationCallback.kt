package com.sparknetwork.personalityassest.data.remote


/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */
interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}