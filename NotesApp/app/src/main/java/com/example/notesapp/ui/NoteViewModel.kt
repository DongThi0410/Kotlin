package com.example.notesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { _notes.value = it }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(Note(title = title, content = content))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    suspend fun getNoteById(id: Int): Note? {
        return repository.getNoteById(id)
    }
}