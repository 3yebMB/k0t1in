package dev.m13d.k0t1in.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.note.NoteActivity
import dev.m13d.k0t1in.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //adapter = MainAdapter()
//        mainRecycler.adapter = adapter
//        viewModel.viewState().observe(this, Observer<MainViewState> { t ->
//            t?.let { adapter.notes = it.notes }
//        })
        fab.setOnClickListener { openNoteScreen(null) }

        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })
    }

    private fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note)
        startActivity(intent)
    }
}