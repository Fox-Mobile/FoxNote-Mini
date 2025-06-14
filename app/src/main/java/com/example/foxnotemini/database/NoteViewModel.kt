package com.example.foxnotemini.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteDao: NoteDao
): ViewModel() {
    private val _state = MutableStateFlow(NoteState())
    private val _notes = noteDao.getNotes()
    val state = combine(_state, _notes) { state, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun OnEvent(event: NoteEvent){
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteDao.deleteNote(event.note)
                }
            }
            NoteEvent.SaveNote -> {
                val title = state.value.title
                val content = state.value.content
                val date = LocalDate.now().toString()

                val note = Note(
                    title = title,
                    content = content,
                    date = date
                )

                viewModelScope.launch {
                    noteDao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        title = "",
                        content = "",
                        date = ""
                    )
                }
            }
            is NoteEvent.SetContent -> {
                _state.update {
                    it.copy(
                        content = event.content
                    )
                }
            }
            is NoteEvent.SetTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
        }
    }
}