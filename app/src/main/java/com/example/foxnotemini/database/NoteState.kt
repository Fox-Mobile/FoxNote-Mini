package com.example.foxnotemini.database

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val date: String = "",
    )
