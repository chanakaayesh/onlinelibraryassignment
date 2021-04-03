package com.chanaka.onlinelibrary.dbhadler;

public class BookModel {

/* */

    private   String  id;
    private   String  Title;
    private   String  Description;
    private   String  Genre;
    private   double  Price;
    private   String  booktImageUrk;

    public BookModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getBooktImageUrk() {
        return booktImageUrk;
    }

    public void setBooktImageUrk(String booktImageUrk) {
        this.booktImageUrk = booktImageUrk;
    }
}
