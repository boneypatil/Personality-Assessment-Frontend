package com.sparknetwork.personalityassest.ui.QuestionAssests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sparknetwork.personalityassest.data.remote.OperationCallback
import com.sparknetwork.personalityassest.data.remote.ResultDataSource
import com.sparknetwork.personalityassest.data.model.DataResult
import com.sparknetwork.personalityassest.data.model.Question


/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */

class MainViewModel(private val repository: ResultDataSource) : ViewModel() {

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _state = MutableLiveData<PersonalityAssestState>()
    val state: LiveData<PersonalityAssestState> = _state

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question> = _question


    var dataResul: DataResult? = null
    private val _category = MutableLiveData<List<String>>()
    val category: LiveData<List<String>> = _category

    var currentQuestions: List<Question>? = null
    var questionIndex = 0
    var selectedCategoryIndex = 0


    init {
        fetchTestData()
    }


    private fun filterWithCategory(): List<Question>? {
        val data = dataResul
        val category = _category.value?.get(selectedCategoryIndex)
        return data?.questions?.filter { it.category.equals(category) }!!
    }

    private fun setQuestion(ques: Question) {
        _question.postValue(ques)
    }

    fun loadQuestions() {
        currentQuestions = filterWithCategory()
        currentQuestions?.get(questionIndex)?.let { setQuestion(it) }
    }

    fun loadNextQuestion() {
        currentQuestions ?: return

        if (questionIndex < currentQuestions!!.size - 1)
            questionIndex++

        if (questionIndex >= currentQuestions?.size!!)
            _state.postValue(PersonalityAssestState.End)
        else
            currentQuestions?.get(questionIndex)?.let { setQuestion(it) }
    }


    fun fetchTestData() {
        _state.postValue(PersonalityAssestState.Loading)
        repository.retrieveTestData(object :
            OperationCallback {
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(obj)


            }

            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)

                if (obj != null && obj is DataResult) {
                    if (obj?.questions?.isEmpty() == true) {

                        _isEmptyList.postValue(true)

                    } else {
                        dataResul = obj
                        _category.postValue(dataResul?.categories)
                        _state.postValue(PersonalityAssestState.Start)
                    }
                }
            }
        })

    }

}

enum class PersonalityAssestState {
    Loading, Start, End
}