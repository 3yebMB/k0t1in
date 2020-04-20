package dev.m13d.k0t1in.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Colour
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.base.BaseActivity
import dev.m13d.k0t1in.viewmodel.note.NoteViewModel
import dev.m13d.k0t1in.viewmodel.note.NoteViewState
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<Note?, NoteViewState>() {
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + " extra​.​NOTE​"
        fun getStartIntent(context: Context, note: String?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }
    }

    override val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteId = intent.getStringExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteId?.let {
            viewModel.loadNote(it)
        }
        if (noteId == null) supportActionBar?.title = getString(R.string.new_note_title)
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    override fun renderData(data: Note?) {
        this.note = data
        initView()
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // not used
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // not used
        }
    }

    private fun triggerSaveNote() {
        if (titleEt.text?.length ?: 0 < 3) return
        Handler().postDelayed({
            note = note?.copy(title = titleEt.text.toString(),
                    note = bodyEt.text.toString(),
                    lastChanged = Date())
                    ?: createNewNote()
            if (note != null) viewModel.saveChanges(note!!)
        }, SAVE_DELAY)
    }

    private fun createNewNote(): Note = Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString())

    private fun initView() {
        if (note != null) {
            titleEt.setText(note?.title ?: "")
            bodyEt.setText(note?.note ?: "")
            val colour = when (note!!.color) {
                Colour.WHITE -> R.color.color_white
                Colour.VIOLET -> R.color.color_violet
                Colour.YELLOW -> R.color.color_yello
                Colour.RED -> R.color.color_red
                Colour.PINK -> R.color.color_pink
                Colour.GREEN -> R.color.color_green
                Colour.BLUE -> R.color.color_blue
            }
            toolbar.setBackgroundColor(resources.getColor(colour))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}