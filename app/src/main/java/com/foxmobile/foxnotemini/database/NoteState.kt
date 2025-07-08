package com.foxmobile.foxnotemini.database

data class NoteState(
    val id : Int? = null,
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val dateTime: String = ""
)