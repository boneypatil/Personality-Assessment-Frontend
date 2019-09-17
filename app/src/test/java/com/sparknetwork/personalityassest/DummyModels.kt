package com.sparknetwork.personalityassest

import com.sparknetwork.personalityassest.data.model.DataResult
import com.sparknetwork.personalityassest.data.model.Question
import com.sparknetwork.personalityassest.data.model.QuestionType
import com.sparknetwork.personalityassest.util.MoshiUtils

/**
 * Created by rahul.p
 * on 9/13/2019
 * as project Personality Assesstment
 *
 */

object DummyModels {


    fun getQuestion(): Question {
        return Question("sgdgdghjsf",
            "What is your gender?",
            "hard_fact",
            QuestionType("single_choice", listOf("male", "female", "other"))
        )
    }

    fun getNullQuestion(): Question {
        return Question("sdfsdfs",
            null,
            "hard_fact",
            QuestionType("single_choice", null)
        )
    }


    fun getQuestionLists():DataResult? {
        val json ="{\n" +
                "\t\"categories\": [\n" +
                "\t\t\"hard_fact\",\n" +
                "\t\t\"lifestyle\",\n" +
                "\t\t\"introversion\",\n" +
                "\t\t\"passion\"\n" +
                "\t],\n" +
                "\t\"questions\": [{\n" +
                "\t\t\t\"question\": \"What is your gender?\",\n" +
                "\t\t\t\"category\": \"hard_fact\",\n" +
                "\t\t\t\"question_type\": {\n" +
                "\t\t\t\t\"type\": \"single_choice\",\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t\"male\",\n" +
                "\t\t\t\t\t\"female\",\n" +
                "\t\t\t\t\t\"other\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"question\": \"How important is the gender of your partner?\",\n" +
                "\t\t\t\"category\": \"hard_fact\",\n" +
                "\t\t\t\"question_type\": {\n" +
                "\t\t\t\t\"type\": \"single_choice\",\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t\"not important\",\n" +
                "\t\t\t\t\t\"important\",\n" +
                "\t\t\t\t\t\"very important\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}"



        return MoshiUtils().deserializeDataResult(json)
    }

}