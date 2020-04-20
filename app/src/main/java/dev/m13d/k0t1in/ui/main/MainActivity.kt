package dev.m13d.k0t1in.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Note
import dev.m13d.k0t1in.ui.base.BaseActivity
import dev.m13d.k0t1in.ui.note.NoteActivity
import dev.m13d.k0t1in.ui.splash.SplashActivity
import dev.m13d.k0t1in.viewmodel.main.MainViewModel
import dev.m13d.k0t1in.viewmodel.main.MainViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>(),
        LogoutDialog.LogoutListener {
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter

    companion object {
        fun getStartIntent(context: Context) = Intent(context,
                MainActivity::class.java)
    }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
            MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.logout -> showLogoutDialog().let { true }
                else -> false
            }

    private fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG)
                ?: LogoutDialog.createInstance().show(supportFragmentManager,
                        LogoutDialog.TAG)
    }

    override fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
    }
}