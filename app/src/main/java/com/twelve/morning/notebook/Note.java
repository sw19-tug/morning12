package com.twelve.morning.notebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Note implements Serializable {
  
    private static Random rand = new Random();

    private String title;
    private String body;
    private Date creation_date;
    private Boolean pinned = false;
    public int id = rand.nextInt();
    private List<Tag> tags;

    public Note(String title){
        this.title = title;
        this.body = "";
        this.creation_date = new Date();
        this.tags = new ArrayList<>();
        //this.tags.add("#testTag");
    }

    public Note(String title, String body){
        this.title = title;
        this.body = body;
        this.creation_date = new Date();
        this.tags = new ArrayList<>();
        //this.tags.add("#testTag");
    }

    public Note(String title, String body, List<Tag> tags){
        this.title = title;
        this.body = body;
        this.creation_date = new Date();
        this.tags = tags;
        //this.tags.add("#testTag");
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

    public void delete(){
        DatabaseWrapper.getInstance().deleteNote(this);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
