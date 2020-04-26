package dev.m13d.k0t1in.ui.note

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.extensions.format
import dev.m13d.k0t1in.extensions.getColorInt
import dev.m13d.k0t1in.model.Color
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.base.BaseActivity
import dev.m13d.k0t1in.viewmodel.note.NoteViewModel
import dev.m13d.k0t1in.viewmodel.note.NoteViewState
import dev.m13d.k0t1in.viewmodel.note.NoteViewState.Data
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

private const val SAVE_DELAY = 2000L

class DeleteDialog : DialogFragment() {
    companion object {
        val TAG = DeleteDialog::class.java.name + "TAG"
        fun createInstance() = DeleteDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_dialog_title)
                    .setMessage(R.string.delete_dialog_message)
                    .setPositiveButton(R.string.ok_bth_title) { _, _ -> (activity as DeleteListener).onDelete() }
                    .setNegativeButton(R.string.logout_dialog_cancel) { _, _ -> dismiss() }
                    .create()

    interface DeleteListener {
        fun onDelete()
    }
}

class NoteActivity : BaseActivity<Data, NoteViewState>(), DeleteDialog.DeleteListener {
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun getStartIntent(context: Context, noteId: String?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            return intent
        }
    }

    override val viewModel: NoteViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null
    private var textWatcher: TextWatcher? = null
    private var color: Color = Color.YELLOW

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

        colorPicker.onColorClickListener = {
            color = it
            setToolbarColor(it)
            triggerSaveNote()
        }
    }

    override fun renderData(data: Data) {
        if (data.isDeleted) finish()

        this.note = data.note
        if(data.note?.color == note?.color &&
                data.note?.title == note?.title &&
                data.note?.note == note?.note) return
        this.note = data.note
        data.note?.let { color = it.color }

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
        if (titleEt.text?.length ?: 0 < 3 || bodyEt.text.length < 3) return

        Handler().postDelayed({
            note = note?.copy(title = titleEt.text.toString(),
                    note = bodyEt.text.toString(),
                    color = color,
                    lastChanged = Date()
            )
                    ?: createNewNote()
            note?.let { viewModel.saveChanges(it) }
        }, SAVE_DELAY)
    }

    private fun setToolbarColor(color: Color) {
        toolbar.setBackgroundColor(color.getColorInt(this))
    }

    private fun createNewNote(): Note = Note(
            UUID.randomUUID().toString(),
            titleEt.text.toString(),
            bodyEt.text.toString()
    )

    private fun initView() {

        note?.run {
            supportActionBar?.title = lastChanged.format()

            removeEditListener()
            titleEt.setText(title)
            bodyEt.setText(note)
            setEditListener()
            toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
            menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> super.onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    override fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    private fun deleteNote() {
        supportFragmentManager.findFragmentByTag(DeleteDialog.TAG) ?:
        DeleteDialog.createInstance().show(supportFragmentManager, DeleteDialog.TAG)
    }

    override fun onDelete() {
        viewModel.deleteNote()
    }

    private fun setEditListener() {
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    private fun removeEditListener() {
        titleEt.removeTextChangedListener(textChangeListener)
        bodyEt.removeTextChangedListener(textChangeListener)
    }
}
