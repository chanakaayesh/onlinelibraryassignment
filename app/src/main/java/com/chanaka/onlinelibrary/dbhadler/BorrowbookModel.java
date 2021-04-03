package com.chanaka.onlinelibrary.dbhadler;

public class BorrowbookModel {
    private String borrowid;
    private String bookid;
    private String bookTitle;

    private String BookimageUrl;
    private String date;
    private int reminingdays;

    public int getReminingdays() {
        return reminingdays;
    }

    public void setReminingdays(int reminingdays) {
        this.reminingdays = reminingdays;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public BorrowbookModel() {
    }

    public BorrowbookModel(String borrowid, String bookid, String bookimageUrl, String date) {
        this.borrowid = borrowid;
        this.bookid = bookid;

        BookimageUrl = bookimageUrl;
        this.date = date;
    }


    public String getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(String borrowid) {
        this.borrowid = borrowid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookimageUrl() {
        return BookimageUrl;
    }

    public void setBookimageUrl(String bookimageUrl) {
        BookimageUrl = bookimageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public static String dupborrowid;
    public static  String dupbookid;
    public static  String dupuserid;
    public static  String dupBookimageUrl;
    public static  String dupdate;

    public static String getDupborrowid() {
        return dupborrowid;
    }

    public static void setDupborrowid(String dupborrowid) {
        BorrowbookModel.dupborrowid = dupborrowid;
    }

    public static String getDupbookid() {
        return dupbookid;
    }

    public static void setDupbookid(String dupbookid) {
        BorrowbookModel.dupbookid = dupbookid;
    }

    public static String getDupuserid() {
        return dupuserid;
    }

    public static void setDupuserid(String dupuserid) {
        BorrowbookModel.dupuserid = dupuserid;
    }

    public static String getDupBookimageUrl() {
        return dupBookimageUrl;
    }

    public static void setDupBookimageUrl(String dupBookimageUrl) {
        BorrowbookModel.dupBookimageUrl = dupBookimageUrl;
    }

    public static String getDupdate() {
        return dupdate;
    }

    public static void setDupdate(String dupdate) {
        BorrowbookModel.dupdate = dupdate;
    }
}
