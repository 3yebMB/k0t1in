package dev.m13d.k0t1in.viewmodel.main

import androidx.lifecycle.Observer
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.NoteResult
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.ui.base.BaseViewModel
import dev.m13d.k0t1in.model.NoteResult.Error

class MainViewModel(private val repository: Repository = Repository) : BaseViewModel<List<Note>?, MainViewState>() {
    private val notesObserver = object : Observer<NoteResult> {
        //Стандартный обсервер LiveData
        override fun onChanged(t: NoteResult?) {
            if (t == null) return
            when (t) {
                is NoteResult.Success<*> -> {
// Может понадобиться вручную импортировать класс ​data.model.NoteResult.Success
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is Error -> {
// Может понадобиться вручную импортировать класс ​data.model.NoteResult.Error
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

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}