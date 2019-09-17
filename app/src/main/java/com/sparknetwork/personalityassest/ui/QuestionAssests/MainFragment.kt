package com.sparknetwork.personalityassest.ui.QuestionAssests

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sparknetwork.personalityassest.R
import com.sparknetwork.personalityassest.data.remote.TAG
import com.sparknetwork.personalityassest.data.model.Question
import com.sparknetwork.personalityassest.di.Injection
import kotlinx.android.synthetic.main.main_fragment.*
import java.lang.RuntimeException


/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */

class MainFragment : Fragment(), OptionClickckedLiscner {


    override fun onOptionClicked(option: String) {
        // Get selected option and store to submit at the end
        viewModel?.loadNextQuestion()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
            .get(MainViewModel::class.java)

        initObservers()
        initListner()
    }

    private fun initListner() {
        btn_submit?.setOnClickListener {
            //On submit trigger submit api
        }
        btn_restart?.setOnClickListener {
           // on restart reload the view and fetch the updated data
            fetchDataAgain()
        }
    }

    /**
     *
     * Reloads the view
     *
     */
    private fun reloadView() {
       // viewModel.questionIndex = 0
        btn_restart?.visibility = View.GONE
        btn_submit?.visibility = View.GONE

        spinnerCategories?.visibility = View.VISIBLE
        recycleOptions?.visibility = View.VISIBLE

        txt_questions?.text = getString(R.string.msg_submit)
    }

    /**
     * Init Observers
     *
     */
    private fun initObservers() {
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
        viewModel.state.observe(this, stateObserver)
    }


    private fun setCategorySpinner(categories: List<String>) {
        viewModel.loadQuestions()

        val catAdapter = activity?.applicationContext?.let { SpinnerAdapter(it, categories) }
        // Set layout to use when the list of choices appear
        // Set Adapter to Spinner
        spinnerCategories?.adapter = catAdapter
        spinnerCategories?.setSelection(viewModel?.selectedCategoryIndex, true)

        spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (activity != null) {
                    viewModel.selectedCategoryIndex = p2
                    viewModel.questionIndex = 0
                    viewModel.loadQuestions()

                }
            }
        }
    }

    /**
     * All the used Observers
     * to populate data and to display error handling messages
     *
     */

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar?.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        val msg = "Error $it"
        showMessage(msg)
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")

        showMessage(getString(R.string.error_string))
    }

    private val stateObserver = Observer<PersonalityAssestState> { state ->
        Log.v(TAG, "emptyListObserver $state")

        when (state) {

            PersonalityAssestState.Loading -> {
                //show progress bar
            }
            PersonalityAssestState.Start -> {

                viewModel.category.observe(this, categoryObserver)
                viewModel.question.observe(this, questionObserver)

            }
            PersonalityAssestState.End -> {
                //submit quiz
                btn_restart?.visibility = View.VISIBLE
                btn_submit?.visibility = View.VISIBLE

                spinnerCategories?.visibility = View.GONE
                recycleOptions?.visibility = View.GONE

                txt_questions?.text = getString(R.string.msg_submit)
            }

            else -> {
                throw RuntimeException("Undefined state found")

            }
        }
    }

    private val questionObserver = Observer<Question> { question ->

        if (!question.isQuestionNullOrBlank()) {
            txt_questions?.text = question.question
            question?.question_type?.options?.let { setOptionsView(it) }
        } else {
            showMessage(getString(R.string.error_string))
        }
    }

    private val categoryObserver = Observer<List<String>> { categories ->
        reloadView()

        categories.let { setCategorySpinner(it) }
    }

    private fun setOptionsView(options: List<String>) {
        val columnsCount = 2

        recycleOptions.layoutManager = GridLayoutManager(this.context, columnsCount)
        recycleOptions.adapter = OptionsAdapter(options, this)
        (recycleOptions.adapter as OptionsAdapter).notifyDataSetChanged()
        recycleOptions.isLayoutFrozen = true

    }

    /**
     * View to display messages and reload data once fails
     *
     */

    fun showMessage(msg: String) =
        Snackbar.make(main, msg, Snackbar.LENGTH_LONG)
            .setAction("Try Again") {
                fetchDataAgain()
            }.show()

   fun fetchDataAgain(){
       viewModel.questionIndex = 0
       viewModel.fetchTestData()
    }
}
