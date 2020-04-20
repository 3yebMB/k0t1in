package dev.m13d.k0t1in.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Colour
import dev.m13d.k0t1in.model.Note

class MainAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

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

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val body = itemView.findViewById<TextView>(R.id.body)
        fun bind(note: Note) {
            title.text = note.title
            body.text = note.note
            val color = when(note.color) {
                Colour.WHITE -> R.color.color_white
                Colour.VIOLET -> R.color.color_violet
                Colour.YELLOW -> R.color.color_yello
                Colour.RED -> R.color.color_red
                Colour.PINK -> R.color.color_pink
                Colour.GREEN -> R.color.color_green
                Colour.BLUE -> R.color.color_blue
            }

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            itemView.setOnClickListener {onItemClickListener.onItemClick(note)}
        }
    }
}
