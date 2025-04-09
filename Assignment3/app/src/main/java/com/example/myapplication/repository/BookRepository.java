package com.example.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.BookDao;
import com.example.myapplication.model.Book;

import java.util.List;

public class BookRepository {
    private BookDao bookDao;
    private LiveData<List<Book>> allBooks;

    public BookRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        bookDao = db.bookDao();
        allBooks = bookDao.getAllBooks();
    }
    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public void insert(Book book) {
        AppDatabase.databaseWriteExecutor.execute(() -> bookDao.insert(book));
    }

    public void update(Book book) {
        AppDatabase.databaseWriteExecutor.execute(() -> bookDao.update(book));
    }

    public void delete(Book book) {
        AppDatabase.databaseWriteExecutor.execute(() -> bookDao.delete(book));
    }
}
