package com.twelve.morning.notebook;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.runner.lifecycle.ApplicationLifecycleCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class DbTest {
    private NoteDatabase testdb;
    private DaoAccess myDao;
    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getContext();
        testdb = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class).build();
        myDao = testdb.daoAccess();
    }

    @After
    public void closeDB() throws IOException {
        testdb.close();
    }

    @Test
    public void DbWriteAndDelete()
    {
        String title = "Title";
        String text = "This is an example text.";
        List<Note> notes = myDao.loadAllNotes();

        Note test_note = new Note(title, text);
        myDao.InsertNote(test_note);
        List<Note> noteList = myDao.loadAllNotes();
        int id = noteList.get(noteList.size() - 1).getId();
        test_note.setId(id);
        Note newNote = myDao.getNoteByTitle((title)).get(0);
        assertEquals(test_note.getBody(), newNote.getBody());
        myDao.deleteNote(test_note);
        noteList = myDao.loadAllNotes();
        assertEquals(true, noteList.isEmpty());
    }

    @Test
    public void DbCreate()
    {
        String title = "Title";
        String text = "This is an example text.";

        Note test_note = new Note(title, text);
        myDao.InsertNote(test_note);
        List<Note> noteList = myDao.loadAllNotes();
        assertEquals(false, noteList.isEmpty());
    }

    @Test
    public void DbEmpty()
    {
        List<Note> noteList = myDao.loadAllNotes();
        assertEquals(true, noteList.isEmpty());
    }

    @Test
    public void DbUpdate()
    {
        String title = "Title";
        String text = "This is an example text.";
        String change = "Now different";

        Note test_note = new Note(title, text);
        myDao.InsertNote(test_note);
        List<Note> noteList = myDao.loadAllNotes();
        Note change_note = noteList.get(0);
        change_note.setBody(change);
        myDao.updateNote(change_note);
        noteList = myDao.loadAllNotes();
        assertEquals(change_note.getBody(), noteList.get(0).getBody());
    }

    @Test
    public void DbTableDrop()
    {
        String titleA = "First";
        String titleB = "Second";
        String titleC = "Third";

        List<Note> noteList = myDao.loadAllNotes();
        assertEquals(true, noteList.isEmpty());
        Note noteA = new Note(titleA);
        myDao.InsertNote(noteA);
        Note noteB = new Note(titleB);
        myDao.InsertNote(noteB);
        Note noteC = new Note(titleC);
        myDao.InsertNote(noteC);
        noteList = myDao.loadAllNotes();
        assertEquals(false, noteList.isEmpty());
        myDao.deleteTable();
        noteList = myDao.loadAllNotes();
        assertEquals(true, noteList.isEmpty());
    }
}