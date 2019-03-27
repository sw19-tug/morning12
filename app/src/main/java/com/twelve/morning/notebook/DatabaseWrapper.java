package com.twelve.morning.notebook;

import java.util.ArrayList;

public class DatabaseWrapper {
    private static volatile DatabaseWrapper singleton = new DatabaseWrapper();

    private DatabaseWrapper(){}

    private ArrayList<Note> notes = new ArrayList<>();

    public static DatabaseWrapper getInstance() {
        return singleton;
    }

    // add new note
    public void addNote(Note new_note) {
        notes.add(new_note);
    }

    // get all notes
    public Note[] getNotes() {
        Note[] tmp = new Note[notes.size()];
        notes.toArray(tmp);
        return tmp;
    }
}
