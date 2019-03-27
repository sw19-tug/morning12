package com.twelve.morning.notebook;


import androidx.room.Database;
import androidx.room.RoomDatabase;

//as Example taken from the Android Development Documentation for Room Databases
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
