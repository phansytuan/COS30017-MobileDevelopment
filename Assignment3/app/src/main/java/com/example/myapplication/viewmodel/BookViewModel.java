package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.Book;
import com.example.myapplication.repository.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private BookRepository repository;
    private LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }
    public void insert(Book book) {
        repository.insert(book);
    }

    public void update(Book book) {
        repository.update(book);
    }
    public void delete(Book book) {
        repository.delete(book);
    }
}
