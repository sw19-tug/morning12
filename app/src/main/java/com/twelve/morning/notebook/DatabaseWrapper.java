package com.twelve.morning.notebook;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DatabaseWrapper {
    private static volatile DatabaseWrapper singleton = new DatabaseWrapper();

    private String DB_NAME = "noteDb";
    private NoteDatabase noteDatabase;
    Context context;

    private DatabaseWrapper()
    {
    }

    private ArrayList<Note> notes = new ArrayList<>();

    public static DatabaseWrapper getInstance() {
        return singleton;
    }

    public void createDatabase(Context context)
    {
        if(noteDatabase != null)
            CloseDB();
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void CloseDB()
    {
        noteDatabase.close();
    }

    public int addNote(Note new_note) {
        noteDatabase.daoAccess().InsertNote(new_note);
        List<Note> notes = noteDatabase.daoAccess().loadAllNotes();

        return notes.get(notes.size()-1).getId();
    }

    public Note[] getNotes(Sorting sorting) {
        List<Note> notes = noteDatabase.daoAccess().loadAllNotes();
        Note[] tmp = new Note[notes.size()];
        notes.toArray(tmp);

        if (sorting == Sorting.CREATION) {
            Arrays.sort(tmp, new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    if (o1.getPinned() && !o2.getPinned()){
                        return -1;
                    }
                    if (!o1.getPinned() && o2.getPinned()){
                        return 1;
                    }
                    return o2.getCreationDate().compareTo(o1.getCreationDate());
                }
            });
        }

        else if (sorting == Sorting.TITLE) {
            Arrays.sort(tmp, new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    if (o1.getPinned() && !o2.getPinned()){
                        return -1;
                    }
                    if (!o1.getPinned() && o2.getPinned()){
                        return 1;
                    }
                    return o1.getTitle().toUpperCase().compareTo(o2.getTitle().toUpperCase());
                }
            });
        }

        else if (sorting == Sorting.SIZE) {
            Arrays.sort(tmp, new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    if (o1.getPinned() && !o2.getPinned()){
                        return -1;
                    }
                    if (!o1.getPinned() && o2.getPinned()){
                        return 1;
                    }
                    return o2.getBody().trim().length() - o1.getBody().trim().length();
                }
            });
        }

        return tmp;
    }

    public void saveNote(Note note) {
        /*for (Note n :
                notes) {
            if (note.id == n.id){
                n.setTitle(note.getTitle());
                n.setPinned(note.getPinned());
                n.setBody(note.getBody());
            }
        }*/
        noteDatabase.daoAccess().updateNote(note);
    }
  
    public void reset(){
        noteDatabase.daoAccess().deleteTable();
    }
  
    public void deleteNote(Note note) {
        /*int index = 0;
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).id == note.id)
            {
                index = i;
                break;
            }
        }
        notes.remove(index);*/
        noteDatabase.daoAccess().deleteNote(note);
    }
}

enum Sorting {
    CREATION,
    TITLE,
    SIZE
}