package dev.m13d.k0t1in.ui

import dev.m13d.k0t1in.model.Result
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.viewmodel.main.MainViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    private val mockRepository: Repository = mockk()
    private val notesLiveData = MutableLiveData<Result>()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { mockRepository.getNotes() } returns notesLiveData
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun should_call_getNotes_once() {
        verify(exactly = 1) { mockRepository.getNotes() }
    }

    @Test
    fun should_return_error() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever { result = it?.error }
        notesLiveData.value = Result.Error(testData)
        assertEquals(result, testData)
    }

    @Test
    fun should_return_Notes() {
        var result: List<Note>? = null
        val testData = listOf(Note(id = "1"), Note(id = "2"))
        viewModel.getViewState().observeForever { result = it?.data}
        notesLiveData.value = Result.Success(testData)
        assertEquals(testData, result)
    }

    @Test
    fun should_remove_observer() {
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }
}