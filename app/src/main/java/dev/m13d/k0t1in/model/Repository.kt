package dev.m13d.k0t1in.model

import androidx.lifecycle.MutableLiveData
import dev.m13d.k0t1in.data.provider.RemoteDataProvider

class Repository(private val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()

    private val notesLiveData = MutableLiveData<List<Note>>()
    private val notes = mutableListOf<Note>()

    init {
        notesLiveData.value = notes
    }

    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)
}