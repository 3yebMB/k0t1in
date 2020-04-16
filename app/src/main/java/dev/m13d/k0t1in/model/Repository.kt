package dev.m13d.k0t1in.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {

    private val notesLiveDate = MutableLiveData<List<Note>>()
    private val notes: MutableList<Note> = mutableListOf()

    init {
        notesLiveDate.value = notes
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveDate
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveDate.value = notes
    }

    private fun addOrReplace(note: Note) {
        for(i in 0 until notes.size) {
            if (notes[i] == note) {
                notes.set(i, note)
                return
            }
        }
        notes.add(note)
    }
}