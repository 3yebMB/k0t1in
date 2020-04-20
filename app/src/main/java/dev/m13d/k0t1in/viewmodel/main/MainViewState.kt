package dev.m13d.k0t1in.viewmodel.main

import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.base.BaseViewState

class MainViewState(notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?>(notes, error)