package com.example.giuaky.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giuaky.data.Note
import com.example.giuaky.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _note = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _note

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { _note.value = it }
        }
    }

    fun addNote(title: String,content: String, imageUrl: String) {
        viewModelScope.launch {
            repository.insert(Note(title = title,content = content, imageUrl = imageUrl ))
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