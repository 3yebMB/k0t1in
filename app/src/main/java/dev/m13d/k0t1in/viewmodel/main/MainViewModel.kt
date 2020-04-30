package dev.m13d.k0t1in.viewmodel.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.Result
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.viewmodel.base.BaseViewModel
import dev.m13d.k0t1in.model.Result.Error

class MainViewModel(private val repository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {
    private val notesObserver = object : Observer<Result> {
        override fun onChanged(t: Result?) {
            if (t == null) return
            when (t) {
                is Result.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is Error -> {
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }
    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    @VisibleForTesting
    public override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}