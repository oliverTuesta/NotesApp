package com.example.notesapp.dao

import androidx.room.*
import com.example.notesapp.entities.Notes

@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes (note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)



}