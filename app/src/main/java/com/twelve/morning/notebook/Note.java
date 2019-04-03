package com.twelve.morning.notebook;

import java.util.Date;
import java.util.Random;

public class Note {
    private static Random rand = new Random();

    private String title;
    private String body;
    private Date creation_date;
    private Boolean pinned = false;
    public int id = rand.nextInt();

    public Note(String title){
        this.title = title;
        this.body = "";
        this.creation_date = new Date();
    }

    public Note(String title, String body){
        this.title = title;
        this.body = body;
        this.creation_date = new Date();
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getBody(){
        return body;
    }


    public Boolean getPinned() {
        return pinned;
    }


    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public Date getCreationDate() {
        return creation_date;
    }


    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void save(){
        DatabaseWrapper.getInstance().saveNote(this);
    }

}
