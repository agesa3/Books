package com.example.booklistactivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    ArrayList<Book> books;

    public BooksAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.book_lis_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book=books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvAuthors;
        TextView tvDate;
        TextView tvPublisher;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthors);
            tvDate = itemView.findViewById(R.id.tvPublishedDate);
            tvPublisher = itemView.findViewById(R.id.tvPublisher);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book) {
            tvTitle.setText(book.title);

            tvAuthors.setText(book.author);
            tvDate.setText(book.publishDate);
            tvPublisher.setText(book.publisher);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Book selectedBook=books.get(position);
            Intent intent=new Intent(v.getContext(),BookDetail.class);
            intent.putExtra("Book",selectedBook);
            v.getContext().startActivity(intent);
        }
    }
}
