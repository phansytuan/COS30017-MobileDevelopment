package com.example.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books ORDER BY title")
    LiveData<List<Book>> getAllBooks();

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);
}
