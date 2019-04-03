package com.twelve.morning.notebook;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "noteTable")
public class Note {

    public static int noteID;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "TextBody")
    private String textBody;

    @ColumnInfo(name = "Tags")
    private String tags;

    Note()
    {

    }

   Note(String inTitle, String inText, String inTags)
    {
        title = inTitle;
        textBody = inText;
        tags = inTags;
        id = noteID;
    }

    public static void SetUp(int id) {
        noteID = id;
        noteID++;
    }

    public void IncrementID() {
        noteID++;
    }

    public static int getNoteId() {
        return noteID;
    }

    public int getId() {
        return id;
    }

    public void setToNoteId() {
        this.id = noteID;
        noteID++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}