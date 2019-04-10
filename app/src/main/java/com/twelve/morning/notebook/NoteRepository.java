package com.twelve.morning.notebook;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class NoteRepository {

    private String DB_NAME = "noteDb";

    private NoteDatabase noteDatabase;
    public NoteRepository(Context context)
    {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void CloseDb()
    {
        noteDatabase.close();
    }

    /*public void Cleanup()
    {
        noteDatabase = null;
    }*/

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

    public List<Note> GetNotesByTitle(String title)
    {
        List<Note> notes = noteDatabase.daoAccess().getNoteByTitle(title);
        return notes;
    }

    public List<Note> GetAllNotes()
    {
        List<Note> notes = noteDatabase.daoAccess().loadAllNotes();
        return notes;
    }

    /*public List<Note> GetAllNotes()
    {
        List<Note> notes = noteDatabase.daoAccess().loadAllNotes();
        return notes;
    }*/

    public void DeleteTable()
    {
        noteDatabase.daoAccess().deleteTable();
    }
}





