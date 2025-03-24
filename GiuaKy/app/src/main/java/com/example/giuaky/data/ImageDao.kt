package com.example.giuaky.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ImageDao {


    @Query("SELECT * FROM images")
    fun getAllImage(): Flow<List<Image>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("SELECT * FROM images WHERE noteId = :id")
     fun getImageByNoteId(id: Int): Image?
}
