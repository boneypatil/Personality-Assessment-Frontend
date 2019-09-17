package com.sparknetwork.personalityassest.data.model



data class Question(var _id : String ,var question: String? = null, var category: String? = null,var question_type: QuestionType? = null) {
    fun isQuestionNullOrBlank(): Boolean {
        return question.isNullOrBlank()
    }

    fun isCategoryNullOrBlank():Boolean{
        return category.isNullOrBlank()
    }

    fun isQuestionTypeNull():Boolean{
        return this.question_type ==null
    }
}