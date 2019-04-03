package com.twelve.morning.notebook;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ApplicationLifecycleCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import java.io.IOError;
import java.io.IOException;
import java.lang.Object;
import java.util.Date;
import java.util.List;
import android.app.Application;
import android.content.Context;
import androidx.lifecycle.Lifecycle;

import static org.junit.Assert.*;

//This DBTest might be obsolete as we now use Room


@RunWith(AndroidJUnit4.class)
public class DbTest {
    //lateinit var testdb: DbHelper //class des DbHelpers
    private NoteDatabase testdb;
    private DaoAccess noteDao;

    @Before
    void setup() {
        //DatabaseHelper testdb = new DatabaseHelper(ApplicationProvider.getApplicationContext());
        Context context = ApplicationProvider.getApplicationContext();
        testdb = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class).build();
        noteDao = testdb.daoAccess();
        //testdb.
    }

    @After
    public void closeDB() throws IOException {
        testdb.close();
    }

    @Test
    public void DbWriteAndRead()
    {
        String title = "Title";
        String text = "This is an example text.";
        String tags = "Surfing, Testing";
        //DatabaseHelper testdb = new DatabaseHelper(something);
        //SETUP FOR THE TABLE
        List<Note> notes = noteDao.loadAllNotes();
        if(notes.size() != 0)
            Note.ResetStaticID(notes.get(notes.size()-1).getId());
        else
            Note.ResetStaticID(-1);
        testdb.DeleteTable();

        Note test_note = new Note(title, text, tags);
        test_note.setId();//Needed to set the id correctly because it might not match the one set in the database

        assertEquals(true, testdb.InsertNote(test_note));
        assertEquals(test_note, testdb.GetNote(test_note.getId()));

        //Date date = ;//date right now

        //alles einzeln asserten/halbwegs einzeln
        //Aufteilen in die drei Bereiche

        //Note* assert_notes = new Note[];
        //testdb.readNotes(assert_notes);
        List<Note> assert_notes;
        assert_notes = testdb.GetAllNotes();

        assertEquals(title, assert_notes.get(0).getTitle());
        assertEquals(text, assert_notes.get(0).getTextBody());
        assertEquals(1, assert_notes.get(0).getId());
        //assertEquals(date, assert_notes.get(0).date);
        //assertEquals(0, assert_notes.get(0).pinned);
        assertEquals(tags, assert_notes.get(0).getTags());
    }

    //@Test

}
