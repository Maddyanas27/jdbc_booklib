package com.collection.Bookshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    List<Book> list = new ArrayList<>();
    public dbconnection db;

    public Inventory() {
        db = new dbconnection();
    }

    // Add a book to the inventory and the database
    public void add(Book a) {
        a.setId(generateId(a));
        
        // Check for uniqueness in the database
        if (isIdUnique(a.getId())) {
            String qry = "INSERT INTO book(Name, Edition, ISBN, Price, Author, Publisher, Genre, ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = db.getDBConnection(); 
                 PreparedStatement pstmt = con.prepareStatement(qry)) {

                pstmt.setString(1, a.getName());
                pstmt.setString(2, a.getEdition());
                pstmt.setString(3, a.getIsbn());
                pstmt.setInt(4, a.getPrice());
                pstmt.setString(5, a.getAuthor());
                pstmt.setString(6, a.getPublisher());
                pstmt.setString(7, a.getGenre());
                pstmt.setString(8, a.getId()); // Add the ID to the insert

                int count = pstmt.executeUpdate();
                if (count == 1) {
                    System.out.println("1 row is inserted");
                    list.add(a); // Optionally add the book to the local list
                } else {
                    throw new Exception("No row is inserted");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("ID already exists. Cannot add the book.");
        }
    }

    // Update an existing book in the inventory and database
    public void update(Book a) {
        String qry = "UPDATE book SET Name=?, Edition=?, ISBN=?, Price=?, Author=?, Publisher=?, Genre=? WHERE ID=?";

        try (Connection con = db.getDBConnection(); 
             PreparedStatement pstmt = con.prepareStatement(qry)) {

            pstmt.setString(1, a.getName());
            pstmt.setString(2, a.getEdition());
            pstmt.setString(3, a.getIsbn());
            pstmt.setInt(4, a.getPrice());
            pstmt.setString(5, a.getAuthor());
            pstmt.setString(6, a.getPublisher());
            pstmt.setString(7, a.getGenre());
            pstmt.setString(8, a.getId());

            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("1 row is updated");
            } else {
                throw new Exception("No row is updated");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Remove a book from the inventory and database by ID
    public void remove(String id) {
        String qry = "DELETE FROM book WHERE ID=?";

        try (Connection con = db.getDBConnection(); 
             PreparedStatement pstmt = con.prepareStatement(qry)) {

            pstmt.setString(1, id);

            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("Successfully removed");
                list.removeIf(book -> book.getId().equals(id)); // Remove from the local list
            } else {
                System.out.println("ID does not match to remove");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Search for a book by ID in the local list or the database
    public Book search(String id) {
        // Search in local list first
        for (Book book : list) {
            if (id.equals(book.getId())) {
                return book;
            }
        }

        // If not found, search in the database
        String qry = "SELECT * FROM book WHERE ID=?";
        try (Connection con = db.getDBConnection(); 
             PreparedStatement pstmt = con.prepareStatement(qry)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                book.setId(id);
                book.setName(rs.getString("Name"));
                book.setEdition(rs.getString("Edition"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPrice(rs.getInt("Price"));
                book.setAuthor(rs.getString("Author"));
                book.setPublisher(rs.getString("Publisher"));
                book.setGenre(rs.getString("Genre"));
                return book;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null; // Return null if not found
    }

    // Show all books in the inventory (both local and database)
    public void show() {
        String qry = "SELECT * FROM book";

        try (Connection con = db.getDBConnection(); 
             PreparedStatement pstmt = con.prepareStatement(qry); 
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("ID") + 
                                   ", Name: " + rs.getString("Name") +
                                   ", Edition: " + rs.getString("Edition") + 
                                   ", ISBN: " + rs.getString("ISBN") +
                                   ", Price: " + rs.getInt("Price") +
                                   ", Author: " + rs.getString("Author") +
                                   ", Publisher: " + rs.getString("Publisher") +
                                   ", Genre: " + rs.getString("Genre"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Generate a unique ID for the book
    public String generateId(Book obj) {
        // Take the first 3 letters of the book name
        String namePart = obj.getName().length() >= 3 ? obj.getName().substring(0, 3).toUpperCase() : obj.getName().toUpperCase();

        // Take the last 2 digits of the edition, if available
        String edition = obj.getEdition();
        String editionPart = edition.length() >= 2 ? edition.substring(edition.length() - 2) : edition;

        // Combine the name and edition parts
        return namePart + editionPart;
    }

    // Check if the ID is unique in the database
    private boolean isIdUnique(String id) {
        String qry = "SELECT COUNT(*) FROM book WHERE ID=?";
        try (Connection con = db.getDBConnection(); 
             PreparedStatement pstmt = con.prepareStatement(qry)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                return true; // ID is unique
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false; // ID is not unique
    }
}
