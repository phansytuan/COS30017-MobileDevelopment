package com.example.myapplication.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book current = books.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView authorText;
        private ImageView genreIcon;
        private View container;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.book_item_container);
            titleText = itemView.findViewById(R.id.book_title);
            authorText = itemView.findViewById(R.id.book_author);
            genreIcon = itemView.findViewById(R.id.book_icon);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(books.get(position));
                }
            });
        }

        public void bind(Book book) {
            titleText.setText(book.getTitle());
            authorText.setText(book.getAuthor());
            // Change background color and icon based on the genre
            if ("Fiction".equalsIgnoreCase(book.getGenre())) {
                container.setBackgroundColor(Color.parseColor("#E0F7FA"));
                genreIcon.setImageResource(R.drawable.ic_fiction);
            } else if ("Non-fiction".equalsIgnoreCase(book.getGenre())) {
                container.setBackgroundColor(Color.parseColor("#FFF9C4"));
                genreIcon.setImageResource(R.drawable.ic_nonfiction);
            } else {
                container.setBackgroundColor(Color.LTGRAY);
                genreIcon.setImageResource(R.drawable.ic_default);
            }
        }
    }
}
