package com.twelve.morning.notebook;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

//as example taken from the Android Development Documentation for Room Databases
@Dao
public interface UserDao{
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE title LIKE :first "
    + "LIMIT 1")
    User findByTitle(String first);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
