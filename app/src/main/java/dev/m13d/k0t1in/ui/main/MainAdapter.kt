package dev.m13d.k0t1in.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.data.model.Note

class MainAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    var notes: List<Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int): Unit {
        holder.bind(notes[position])
    }
}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val body = itemView.findViewById<TextView>(R.id.body)

    fun bind(note: Note) {
        with(note) {
            this@NoteViewHolder.title.text = title
            body.text = this.note
            itemView.setBackgroundColor(with(note) {colour})
        }
    }
}