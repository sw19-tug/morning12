package com.twelve.morning.notebook;

import java.util.Date;
import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "noteTable")
public class Note {

    public static int staticID;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Body")
    private String body;

    @ColumnInfo(name = "Tags")
    private String tags;

    @ColumnInfo(name = "Date")
    private Date creation_date;

    @ColumnInfo(name = "Pinned")
    private Boolean pinned = false;

    Note()
    {
        id = staticID;
        creation_date = new Date();
        pinned = false;
    }

    Note(String inTitle)
    {
        title = inTitle;
        id = staticID;
        creation_date = new Date();
    }

    Note(String inTitle, String inText)
    {
        title = inTitle;
        body = inText;
        id = staticID;
        creation_date = new Date();
    }

   Note(String inTitle, String inText, String inTags)
    {
        title = inTitle;
        body = inText;
        tags = inTags;
        id = staticID;
        creation_date = new Date();
    }

    Note(Note inNote)
    {
        title = inNote.title;
        body = inNote.body;
        tags = inNote.tags;
        id = inNote.id;
        creation_date = inNote.creation_date;
    }

    public static void ResetStaticID(int id) {
        staticID = id;
        staticID++;
    }

    public void IncrementStaticID() {
        staticID++;
    }

    public static int getStaticId() {
        return staticID;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = staticID;
        staticID++;
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
        return body;
    }

    public void setTextBody(String textBody) {
        this.body = textBody;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getPinned() { return pinned; }

    public void setPinned(Boolean pinned) { this.pinned = pinned; }
}