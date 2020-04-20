package dev.m13d.k0t1in.viewmodel.note

import androidx.lifecycle.Observer
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.NoteResult
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.ui.base.BaseViewModel
import dev.m13d.k0t1in.model.NoteResult.Error

class NoteViewModel(private val repository: Repository = Repository): BaseViewModel<Note?, NoteViewState>() {
    private var pendingNote: Note? = null
    fun saveChanges(note: Note) {
        pendingNote = note
    }
    override fun onCleared() {
        if (pendingNote != null) {
        repository.saveNote(pendingNote!!)
    }
    }
    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever(object :
                Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            if (t == null) return
            when(t) {
                is NoteResult.Success<*> ->
                viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                is Error ->
                viewStateLiveData.value = NoteViewState(error = t.error)
            }
        }
    })
    }
}