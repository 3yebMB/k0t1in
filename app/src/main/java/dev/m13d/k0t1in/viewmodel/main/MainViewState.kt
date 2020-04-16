package dev.m13d.k0t1in.viewmodel.main

import androidx.lifecycle.LiveData
import dev.m13d.k0t1in.model.Note

data class MainViewState(val notes: List<Note>)