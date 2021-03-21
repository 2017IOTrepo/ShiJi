package com.androidlab.shiji.bean;

import com.androidlab.shiji.activity.popular_science.Sci_Book;

import java.io.Serializable;

public class Sci_Book_Show implements Serializable {
    private int Book_Image;
    private String Book_tag;
    private String Book_Title;
    private String Book_Introduce;
    private String Boon_Author;

     public Sci_Book_Show(int a, String Book_title, String Book_Author, String Book_Introduce, String Book_tag){
         this.Book_Image = a;
         this.Book_Title = Book_title;
         this.Boon_Author = Book_Author;
         this.Book_Introduce = Book_Introduce;
         this.Book_tag = Book_tag;
     }

    public int getBook_Image() {
        return Book_Image;
    }

    public void setBook_Image(int book_Image) {
        Book_Image = book_Image;
    }

    public String getBook_tag() {
        return Book_tag;
    }

    public void setBook_tag(String book_tag) {
        Book_tag = book_tag;
    }

    public String getBook_Title() {
        return Book_Title;
    }

    public void setBook_Title(String book_Title) {
        Book_Title = book_Title;
    }

    public String getBook_Introduce() {
        return Book_Introduce;
    }

    public void setBook_Introduce(String book_Introduce) {
        Book_Introduce = book_Introduce;
    }

    public String getBoon_Author() {
        return Boon_Author;
    }

    public void setBoon_Author(String boon_Author) {
        Boon_Author = boon_Author;
    }
}
