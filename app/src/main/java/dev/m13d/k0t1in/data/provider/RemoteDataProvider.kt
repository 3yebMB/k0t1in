package dev.m13d.k0t1in.data.provider

import androidx.lifecycle.LiveData
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.NoteResult
import dev.m13d.k0t1in.model.User

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
}