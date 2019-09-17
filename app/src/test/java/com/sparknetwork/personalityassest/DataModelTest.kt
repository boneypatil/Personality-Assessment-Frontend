package com.sparknetwork.personalityassest

import org.junit.Assert
import org.junit.Test
import kotlin.test.*

/**
 * Created by rahul
 * on 9/13/2019
 * as project Personality Assesstment
 *
 */

class DataModelTest{

    @Test
    fun isMoshiParsingDataProperly(){
        val dataResult = DummyModels.getQuestionLists()

        assertNotNull(dataResult)
        assertNotNull(dataResult.categories)
        assertNotNull(dataResult.questions)
        assertNotNull(dataResult.questions!![0].question_type)
        assertNotNull(dataResult.questions!![0].question_type?.options!![0])

        assertEquals(4, dataResult.categories!!.size)
        assertEquals("What is your gender?",dataResult.questions!![0].question)
        assertEquals("hard_fact",dataResult.questions!![0].category)
        assertEquals("male",dataResult.questions!![0].question_type?.options!![0])


    }

    @Test
    fun modelQuestionValidation(){
        val question = DummyModels.getQuestion()

        assertFalse{question.isQuestionNullOrBlank()}
        assertFalse{question.isCategoryNullOrBlank()}
        assertNotNull(question.isQuestionTypeNull())
        assertNotNull(question.question_type?.options.isNullOrEmpty())
    }

    @Test
    fun modelNullQuestionValidation(){
        val question = DummyModels.getNullQuestion()

        assertNull(question.question)
        assertNull(question.question_type?.options)
    }
}