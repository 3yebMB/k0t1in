package dev.m13d.k0t1in.viewmodel.note

import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.model.Result
import dev.m13d.k0t1in.model.Result.Error
import dev.m13d.k0t1in.viewmodel.base.BaseViewModel
import dev.m13d.k0t1in.viewmodel.note.NoteViewState.Data

class NoteViewModel(private val repository: Repository) : BaseViewModel<Data, NoteViewState>() {

    fun saveChanges(note: Note) {
        viewStateLiveData.value = NoteViewState(Data(note = note))
    }

    override fun onCleared() {
        currentNote?.let { repository.saveNote(it) }
    }

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun deleteNote() {
        currentNote?.let {
            repository.deleteNote(it.id).observeForever { t ->
                t?.let {
                    viewStateLiveData.value = when (it) {
                        is Result.Success<*> -> NoteViewState(Data(isDeleted = true))
                        is Error -> NoteViewState(error = it.error)
                    }
                }
            }
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { t ->
            t?.let {
                viewStateLiveData.value = when (t) {
                    is Result.Success<*> -> NoteViewState(Data(note = t.data as? Note))
                    is Error -> NoteViewState(error = t.error)
                }
            }
        }
    }

}