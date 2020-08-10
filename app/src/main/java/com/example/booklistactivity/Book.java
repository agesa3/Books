package com.example.booklistactivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class Book implements Parcelable {
    public String id;
    public String title;
    public String subTitle;
    public String author;
    public String publisher;
    public String publishDate;
    public String description;
//    public String thumbnail;

    public Book(String id, String title, String subTitle, String[] author, String publisher, String publishDate, String description) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.author = TextUtils.join(",", author);
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        // this.thumbnail = thumbnail;

    }


    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        author = in.readString();
        publisher = in.readString();
        publishDate = in.readString();
        description = in.readString();
        //thumbnail = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(publishDate);
        dest.writeString(description);
        //  dest.writeString(thumbnail);
    }

    //@BindingAdapter({"android:imageUrl"})
//    public static void loadImage(ImageView view, String imageUrl) {
//        if (!imageUrl.isEmpty()) {
//            Picasso.with(view.getContext())
//                    .load(imageUrl)
//                    .placeholder(R.drawable.book_open)
//                    .into(view);
//        }
//        else {
//            view.setBackgroundResource(R.drawable.book_open);
//        }
//    }
}
