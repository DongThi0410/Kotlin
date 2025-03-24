package com.example.giuaky.repository

import com.example.giuaky.data.Note
import com.example.giuaky.data.NoteDao
import kotlinx.coroutines.flow.Flow


class NoteRepository(private val productDao: NoteDao) {
    fun getAllNotes(): Flow<List<Note>> = productDao.getAllNotes()

    suspend fun insert(note: Note) {
        productDao.insert(note)
    }

    suspend fun delete(note: Note) {
        productDao.delete(note)
    }
    suspend fun update(note: Note) = productDao.update(note)

    suspend fun getNoteById(id: Int): Note? = productDao.getNoteById(id)
}