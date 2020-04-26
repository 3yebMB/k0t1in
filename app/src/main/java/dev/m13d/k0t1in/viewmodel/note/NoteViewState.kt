package dev.m13d.k0t1in.viewmodel.note

import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.viewmodel.base.BaseViewState
import dev.m13d.k0t1in.viewmodel.note.NoteViewState.Data

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
        BaseViewState<Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}