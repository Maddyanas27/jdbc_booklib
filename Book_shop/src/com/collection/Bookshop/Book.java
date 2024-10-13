package com.collection.Bookshop;

public class Book {
    private String name;
    private String edition;
    private String isbn;
    private String id;         // Book ID
    private int price;
    private String author;     // New attribute
    private String publisher;  // New attribute
    private String genre;      // New attribute

    public Book() {
        this.name = null;
        this.edition = null;
        this.isbn = null;
        this.id = null;
        this.price = 0;
        this.author = null;
        this.publisher = null;
        this.genre = null;
    }

    // Getters and Setters for new attributes
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Existing getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getId() {
        return id;  // Getter for Book ID
    }

    public void setId(String id) {
        this.id = id;  // Setter for Book ID
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Enhanced toString method for better formatting
    @Override
    public String toString() {
        return "\n=== Book Information ===" +
                "\nName      : " + name +
                "\nEdition   : " + edition +
                "\nISBN      : " + isbn +
                "\nID        : " + id +
                "\nPrice     : " + price +
                "\nAuthor    : " + author +
                "\nPublisher : " + publisher +
                "\nGenre     : " + genre +
                "\n========================\n";
    }
}
