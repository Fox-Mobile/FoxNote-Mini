package com.example.foxnotemini.database

sealed interface NoteEvent {
    object SaveNote : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
    data class SetTitle(val title: String) : NoteEvent
    data class SetContent(val content: String) : NoteEvent
    data class SetID(val id: Int?) : NoteEvent
}