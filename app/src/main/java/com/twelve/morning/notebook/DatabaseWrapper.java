package com.twelve.morning.notebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
    public Note[] getNotes(Sorting sorting) {
        Note[] tmp = new Note[notes.size()];
        notes.toArray(tmp);

        if (sorting == Sorting.CREATION) {
            Arrays.sort(tmp, new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    return o2.getCreationDate().compareTo(o1.getCreationDate());
                }
            });
        }

        if (sorting == Sorting.TITLE) {
            Arrays.sort(tmp, new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        return tmp;
    }

    public void saveNote(Note note) {
        for (Note n :
                notes) {
            if (note.id == n.id){
                n.setTitle(note.getTitle());
                n.setPinned(note.getPinned());
                n.setBody(note.getBody());
            }
        }
    }
    public void reset(){
        this.notes.clear();
    }
}

enum Sorting {
    CREATION,
    TITLE
}