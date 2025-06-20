package com.example.foxnotemini.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foxnotemini.R
import com.example.foxnotemini.database.Note

class RecyclerViewAdapter(
    private var notes: List<Note>,
    private val onClickedNote: (note: Note) -> Unit,
    private val onDeleteNoteButtonClicked: (note: Note) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val date: TextView

        init {
            title = view.findViewById(R.id.titleView)
            date = view.findViewById(R.id.dateView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.thumbnail_note, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = notes[position].title
        holder.date.text = notes[position].date

        holder.itemView.setOnClickListener {
            onClickedNote(notes[position])
        }

        holder.itemView.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener {
            onDeleteNoteButtonClicked(notes[position])
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateData(newNotes: List<Note>) {
        this.notes = newNotes
        notifyDataSetChanged()
    }
}