package dev.m13d.k0t1in.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.m13d.k0t1in.R

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pushme(view: View) {
        Toast.makeText(this, "Thanks a lot", Toast.LENGTH_LONG).show()
    }
}