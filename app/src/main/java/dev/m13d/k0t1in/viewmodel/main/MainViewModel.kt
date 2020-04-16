package dev.m13d.k0t1in.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.m13d.k0t1in.model.Repository

class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
//        viewStateLiveData.value = MainViewState(Repository.getNotes())
        Repository.getNotes().observeForever {
            viewStateLiveData.value =
                    viewStateLiveData.value?.copy(notes = it!!) ?:
                            MainViewState(it!!)
        }
    }

    fun viewState() : LiveData<MainViewState> = viewStateLiveData

}