package dev.m13d.k0t1in.viewmodel.note

import androidx.lifecycle.ViewModel
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.Repository

class NoteViewModel(private val repository: Repository = Repository) : ViewModel() {
    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }
}