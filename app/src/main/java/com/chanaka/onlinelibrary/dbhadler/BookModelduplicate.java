package com.chanaka.onlinelibrary.dbhadler;

public class BookModelduplicate {
    public static String  id;
    public static String  Title;
    public static String  Description;
    public static String  Genre;
    public static double  Price;
    public static String  booktImageUrk;

    public BookModelduplicate() {
    }


    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        BookModelduplicate.id = id;
    }

    public static String getTitle() {
        return Title;
    }

    public static void setTitle(String title) {
        Title = title;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static String getGenre() {
        return Genre;
    }

    public static void setGenre(String genre) {
        Genre = genre;
    }

    public static double getPrice() {
        return Price;
    }

    public static void setPrice(double price) {
        Price = price;
    }

    public static String getBooktImageUrk() {
        return booktImageUrk;
    }

    public static void setBooktImageUrk(String booktImageUrk) {
        BookModelduplicate.booktImageUrk = booktImageUrk;
    }
}
