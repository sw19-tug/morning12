package com.twelve.morning.notebook;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

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

    @ColumnInfo(name = "Latitude")
    private Double latitude;

    @ColumnInfo(name = "Longitude")
    private Double longitude;

    @ColumnInfo(name = "address")
    private String address;

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
        longitude = inNote.longitude;
        latitude = inNote.latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public LatLng getLocation(){
        if(this.latitude != null && this.longitude != null){
            return new LatLng(this.latitude, this.longitude);
        }
        else {
            return new LatLng(0.0, 0.0);
        }
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
}
