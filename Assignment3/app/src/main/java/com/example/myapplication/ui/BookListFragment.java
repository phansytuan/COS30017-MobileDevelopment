package com.example.myapplication.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.viewmodel.BookViewModel;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookListFragment extends Fragment {
    private BookViewModel viewModel;
    private BookAdapter adapter;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_books);
        adapter = new BookAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(book -> {
            // Pass book details via Bundle for editing
            Bundle bundle = new Bundle();
            bundle.putInt("id", book.getId());
            bundle.putString("title", book.getTitle());
            bundle.putString("author", book.getAuthor());
            bundle.putString("genre", book.getGenre());
            Navigation.findNavController(view)
                    .navigate(R.id.action_bookListFragment_to_bookDetailFragment, bundle);
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_book);
        fab.setOnClickListener(v -> {
            // Navigate to detail fragment for adding a new book (no arguments)
            Navigation.findNavController(view)
                    .navigate(R.id.action_bookListFragment_to_bookDetailFragment);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);
        viewModel.getAllBooks().observe(getViewLifecycleOwner(), books -> {
            adapter.setBooks(books);
        });
    }
}
