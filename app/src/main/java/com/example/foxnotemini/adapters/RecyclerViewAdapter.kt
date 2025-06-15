package com.example.foxnotemini.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foxnotemini.R
import com.example.foxnotemini.database.Note
import kotlin.collections.addAll
import kotlin.text.clear

class RecyclerViewAdapter(
    private var notes: List<Note>,
    private val onClickedNote: (note: Note) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(View: View) : RecyclerView.ViewHolder(View) {
        val title: TextView
        val date: TextView

        init {
            title = View.findViewById(R.id.titleView)
            date = View.findViewById(R.id.dateView)
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
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

}