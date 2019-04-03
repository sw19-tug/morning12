package com.twelve.morning.notebook;

import android.content.Context;
import java.util.List;
import androidx.room.Room;

public class NoteRepository {

    private String DB_NAME = "noteDb";

    private NoteDatabase noteDatabase;
    public NoteRepository(Context context)
    {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void Cleanup()
    {
        noteDatabase = null;
    }

    public boolean InsertNote(final Note note)
    {
        noteDatabase.daoAccess().InsertNote(note);

        return true;
    }

    public void UpdateNote(final Note note)
    {
        noteDatabase.daoAccess().updateNote(note);
    }

    public void DeleteNote(final Note note)
    {
        noteDatabase.daoAccess().deleteNote(note);
    }

    public Note GetNote(int id)
    {
        //notes are saved on id+1 position for some reason
        Note note = noteDatabase.daoAccess().getNote(id+1);
        return note;
    }

    public List<Note> GetAllNotes()
    {
        List<Note> notes = noteDatabase.daoAccess().loadAllNotes();
        return notes;
    }

    public void DeleteTable()
    {
        noteDatabase.daoAccess().deleteTable();
    }
}


