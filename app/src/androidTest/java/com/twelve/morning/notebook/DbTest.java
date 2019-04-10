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
    //private NoteRepository testdb;
    private NoteDatabase testdb;
    private DaoAccess myDao;
    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getContext();
        //testdb = new NoteRepository(context);
        testdb = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class).build();
        myDao = testdb.daoAccess();
    }

    @After
    public void closeDB() throws IOException {
        //testdb.CloseDb();
        testdb.close();
    }

    @Test
    public void DbWriteAndDelete()
    {
        String title = "Title";
        String text = "This is an example text.";
        String tags = "Surfing, Testing";
        //List<Note> notes = testdb.GetAllNotes();
        List<Note> notes = myDao.loadAllNotes();

        Note test_note = new Note(title, text, tags);
        //test_note.setId();//Needed to set the id correctly because it might not match the one set in the database

        //assertEquals(true, testdb.InsertNote(test_note));
        //assertEquals(true, myDao.InsertNote(test_note));
        myDao.InsertNote(test_note);
        List<Note> noteList = myDao.loadAllNotes();
        int id = noteList.get(noteList.size() - 1).getId();
        test_note.setId(id);
        Note newNote = myDao.getNoteByTitle((title)).get(0);
        assertEquals(test_note.getBody(), newNote.getBody());
        myDao.deleteNote(test_note);
        noteList = myDao.loadAllNotes();
        assertEquals(true, noteList.isEmpty());


        //Date date = ;//date right now

        //alles einzeln asserten/halbwegs einzeln
        //Aufteilen in die drei Bereiche

        //Note* assert_notes = new Note[];
        //testdb.readNotes(assert_notes);
        //List<Note> assert_notes;
        //assert_notes = noteDao.loadAllNotes();

        //assertEquals(title, assert_notes.get(0).getTitle());
        //assertEquals(text, assert_notes.get(0).getBody());
        //assertEquals(1, assert_notes.get(0).getId());
        //assertEquals(date, assert_notes.get(0).date);
        //assertEquals(0, assert_notes.get(0).pinned);
        //assertEquals(tags, assert_notes.get(0).getTags());
    }
}