package com.androidlab.shiji.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Book_Spread implements Serializable {

    private int Book_Image;
    private String Book_Name;
    private String Book_Content;

    public Book_Spread(int Book_Image, String Book_Name, String Book_Content){
        this.Book_Image = Book_Image;
        this.Book_Name = Book_Name;
        this.Book_Content = Book_Content;
    }

    @Override
    public String toString() {
        return "Book_Spread{" +
                "Book_Image='" + Book_Image + '\'' +
                ", Book_Name='" + Book_Name + '\'' +
                ", Book_Content='" + Book_Content + '\'' +
                '}';
    }

    public int getBook_Image() {
        return Book_Image;
    }

    public void setBook_Image(int book_Image) {
        Book_Image = book_Image;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }

    public String getBook_Content() {
        return Book_Content;
    }

    public void setBook_Content(String book_Content) {
        Book_Content = book_Content;
    }
}
