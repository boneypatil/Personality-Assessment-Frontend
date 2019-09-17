package com.sparknetwork.personalityassest

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sparknetwork.personalityassest.data.remote.OperationCallback
import com.sparknetwork.personalityassest.data.remote.ResultDataSource
import com.sparknetwork.personalityassest.data.model.DataResult
import com.sparknetwork.personalityassest.ui.QuestionAssests.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify


/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */
class ViewModelTest {

    @Mock
    private lateinit var repository: ResultDataSource
    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback>

    private lateinit var viewModel: MainViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMuseumsObserver: Observer<List<String>>

    private lateinit var dataEmptyResult: DataResult
    private lateinit var dataResult: DataResult

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)

        viewModel = MainViewModel(repository)

        mockData()
        setupObservers()
    }

    @Test
    fun emptyListRepositoryAndViewModel() {
        //empty object  has null values

        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
            isEmptyList.observeForever(emptyListObserver)
            category.observeForever(onRenderMuseumsObserver)
        }

        verify(repository, times(1)).retrieveTestData(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(dataEmptyResult)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNull(viewModel.isEmptyList.value)
        Assert.assertNull(viewModel.category?.value)
    }

    @Test
    fun failRepositoryAndViewModel() {
        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(repository, times(1)).retrieveTestData(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("Ocurring un error")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    @Test
    fun museumListRepositoryAndViewModel() {
        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            category.observeForever(onRenderMuseumsObserver)
        }

        verify(repository, times(1)).retrieveTestData(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(dataResult)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.category.value?.size == 4)
    }

    private fun setupObservers() {
        isViewLoadingObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = Mockito.mock(Observer::class.java) as Observer<Any>
        emptyListObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver = Mockito.mock(Observer::class.java) as Observer<List<String>>
    }

    private fun mockData() {

        this.dataEmptyResult = DataResult()

        dataResult = DummyModels.getQuestionLists()!!
    }
}