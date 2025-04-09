package com.example.myapplication.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.viewmodel.BookViewModel;
import com.example.myapplication.R;
import com.example.myapplication.model.Book;
import com.google.android.material.button.MaterialButton;

public class BookDetailFragment extends Fragment {
    private EditText editTitle, editAuthor, editGenre;
    private MaterialButton buttonSave, buttonDelete;
    private BookViewModel viewModel;
    private int bookId = -1; // Default indicates a new book

    public BookDetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        editTitle = view.findViewById(R.id.edit_text_title);
        editAuthor = view.findViewById(R.id.edit_text_author);
        editGenre = view.findViewById(R.id.edit_text_genre);
        buttonSave = view.findViewById(R.id.button_save);
        buttonDelete = view.findViewById(R.id.button_delete);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        // If arguments exist, we are in edit mode
        if (getArguments() != null && getArguments().containsKey("id")) {
            bookId = getArguments().getInt("id");
            editTitle.setText(getArguments().getString("title", ""));
            editAuthor.setText(getArguments().getString("author", ""));
            editGenre.setText(getArguments().getString("genre", ""));
            buttonDelete.setVisibility(View.VISIBLE);
        } else {
            buttonDelete.setVisibility(View.GONE);
        }

        buttonSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String author = editAuthor.getText().toString().trim();
            String genre = editGenre.getText().toString().trim();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author)) {
                Toast.makeText(getContext(), "Title and Author are required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (bookId == -1) {
                // New book – insert
                Book book = new Book(title, author, genre);
                viewModel.insert(book);
                Toast.makeText(getContext(), "Book added", Toast.LENGTH_SHORT).show();
            } else {
                // Existing book – update
                Book book = new Book(title, author, genre);
                book.setId(bookId);
                viewModel.update(book);
                Toast.makeText(getContext(), "Book updated", Toast.LENGTH_SHORT).show();
            }
            Navigation.findNavController(view).navigateUp();
        });

        buttonDelete.setOnClickListener(v -> {
            if (bookId != -1) {
                Book book = new Book(editTitle.getText().toString(), editAuthor.getText().toString(), editGenre.getText().toString());
                book.setId(bookId);
                viewModel.delete(book);
                Toast.makeText(getContext(), "Book deleted", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigateUp();
            }
        });
    }
}
