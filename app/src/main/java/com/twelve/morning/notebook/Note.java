package com.twelve.morning.notebook;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "noteTable")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Body")
    private String body;

    @ColumnInfo(name = "Date")
    private Date creationDate;

    @ColumnInfo(name = "Pinned")
    private Boolean pinned = false;

    @ColumnInfo(name = "Selected")
    private Boolean selected = false;


    Note()
    {
        creationDate = new Date();
        pinned = false;
    }

    Note(String inTitle)
    {
        title = inTitle;
        creationDate = new Date();
    }

    Note(String inTitle, String inText)
    {
        title = inTitle;
        body = inText;
        creationDate = new Date();
    }

    Note(Note inNote)
    {
        title = inNote.title;
        body = inNote.body;
        id = inNote.id;
        creationDate = inNote.creationDate;
    }

    public void save(){
        DatabaseWrapper.getInstance().saveNote(this);
    }

    public void delete(){
        DatabaseWrapper.getInstance().deleteNote(this);
    }

    public int getId() {
        return id;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTextBody(String textBody) {
        this.body = textBody;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creation_date) {
        this.creationDate = creation_date;
    }

    public Boolean getPinned() { return pinned; }

    public void setPinned(Boolean pinned) { this.pinned = pinned; }

    public Boolean getSelected() { return selected; }

    public void setSelected(Boolean selected) { this.selected = selected; }

    public List<Tag> getTags() { return new ArrayList<>(); }
}
