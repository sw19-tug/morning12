package com.twelve.morning.notebook;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void InsertNote(Note note);

    @Query("SELECT * FROM noteTable WHERE title = :noteTitle")
    List<Note> getNoteByTitle(String noteTitle);

    @Query("SELECT * FROM noteTable")
    List<Note> loadAllNotes();

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM noteTable")
    void deleteTable();
}
