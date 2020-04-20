package dev.m13d.k0t1in.viewmodel.note

import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null): BaseViewState<Note?>(note, error)