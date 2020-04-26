package dev.m13d.k0t1in.data.provider

import androidx.lifecycle.LiveData
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.model.Result
import dev.m13d.k0t1in.model.User

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<Result>
    fun getNoteById(id: String): LiveData<Result>
    fun saveNote(note: Note) : LiveData<Result>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote(noteId: String): LiveData<Result>
}