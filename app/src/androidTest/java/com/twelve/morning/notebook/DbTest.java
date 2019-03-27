package com.twelve.morning.notebook;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ApplicationLifecycleCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;

//This DBTest might be obsolete as we now use Room


@RunWith(AndroidJUnit4.class)
public class DbTest {
    //lateinit var testdb: DbHelper //class des DbHelpers

    @Before
    void setup() {
        DatabaseHelper testdb = new DatabaseHelper(ApplicationProvider.getApplicationContext());
        testdb.
    }

    @After
    void cleaningDB() {
        testdb.cleanUp;
    }

    @Test
    public void DbWriteAndRead()
    {
        String title = "Title";
        String text = "This is an example text.";
        String tags = "Surfing, Testing";
        Note test_note = new Note(title, text, tags);
        DatabaseHelper testdb = new DatabaseHelper(something);

        assertEquals(true, testdb.addNote(test_note));
        assertEquals(note, testdb.loadNote(id));

        Date date = ;//date right now

        //alles einzeln asserten/halbwegs einzeln
        //Aufteilen in die drei Bereiche

        Note* assert_notes = new Note[];
        testdb.readNotes(assert_notes);

        assertEquals(title, assert_notes[0].title);
        assertEquals(text, assert_notes[0].text);
        assertEquals(1, assert_notes[0].id);
        assertEquals(date, assert_notes[0].date);
        assertEquals(0, assert_notes[0].pinned);
        assertEquals(tags, assert_notes[0].tags);
    }

    @Test

}
