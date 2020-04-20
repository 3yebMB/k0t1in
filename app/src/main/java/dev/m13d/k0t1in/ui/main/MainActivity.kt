package dev.m13d.k0t1in.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.base.BaseActivity
import dev.m13d.k0t1in.ui.note.NoteActivity
import dev.m13d.k0t1in.viewmodel.main.MainViewModel
import dev.m13d.k0t1in.viewmodel.main.MainViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java) }
    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
        override fun onItemClick(note: Note) {
            openNoteScreen(note)
        }
    })
        mainRecycler.adapter = adapter
        fab.setOnClickListener { openNoteScreen(null) }
    }
    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }
    private fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note?.id)
        startActivity(intent)
    }
}