package com.twelve.morning.notebook;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class NoteDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}