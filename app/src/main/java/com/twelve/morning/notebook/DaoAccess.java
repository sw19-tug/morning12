package com.twelve.morning.notebook;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DaoAccess {

    @Insert
    void InsertNote(Note note);

    @Query("SELECT * FROM noteTable WHERE id = :taskID")
    Note getNote(int taskID);

    @Query("SELECT * FROM noteTable")
    List<Note> loadAllNotes();
    //Note[] loadAllNotes();

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM noteTable")
    void deleteTable();
}
