package com.example.foxnotemini.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
    val notes = viewModelScope.async {noteDao.getNotes()}

    fun OnEvent(event: NoteEvent){
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteDao.deleteNote(event.note)
                }
            }
            NoteEvent.SaveNote -> {
                val id = _state.value.id
                val title = _state.value.title
                val content = _state.value.content
                val date = LocalDate.now().toString()
                val dateTime = LocalDateTime.now().toString()

                val note = Note(
                    id = id,
                    title = title,
                    content = content,
                    date = date,
                    dateTime = dateTime
                )

                viewModelScope.launch {
                    noteDao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        id = null,
                        title = "",
                        content = "",
                        date = "",
                        dateTime = ""
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

            is NoteEvent.SetID -> {
                _state.update {
                    it.copy(
                        id = event.id
                    )
                }
            }
        }
    }
}