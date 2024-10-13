package com.anas.collection.Bookshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.collection.Bookshop.Book;
import com.collection.Bookshop.Inventory;
import com.collection.Bookshop.invalidpassexception;

public class Main {
    static List<Login> logins = new ArrayList<>();
    
    public static void main(String[] args) {
        Inventory inv = new Inventory();
        Scanner sc = new Scanner(System.in);
        int opt;
        
        do {
            System.out.print("*** WELCOME KONGU LIBRARY ***\n");
            System.out.print("1. Login \n2. Signup  ");
            opt = sc.nextInt();
            
            switch (opt) {
                case 1:
                    handleLogin(sc, inv);
                    break;
                    
                case 2:
                    handleSignup(sc);
                    break;
                    
                case 3:
                    System.out.println("Exiting...");
                    break;
                    
                default:
                    System.out.println("Invalid option");
            }
        } while (opt != 3);
        
        sc.close();
    }
    
    private static void handleLogin(Scanner sc, Inventory inv) {
        System.out.print("Enter your Username: ");
        String username = sc.next();
        Login l = checkrole(username);
        
        if (l != null) {
            if (l.getRole().equalsIgnoreCase("Admin")) {
                adminInventory(inv);
            } else {
                customerInventory(inv);
            }
        } else {
            System.out.println("You have no account.");
        }
    }
    
    private static void handleSignup(Scanner sc) {
        System.out.print("Enter your Username: ");
        String name = sc.next();
        
        if (logins.stream().anyMatch(login -> login.getUname().equals(name))) {
            System.out.println("You already have an account.");
        } else {
            Login l = new Login();
            l.setUname(name);
            System.out.print("Enter your password: ");
            l.setpassword(sc.next());
            System.out.print("Enter your role (Admin/User): ");
            l.setRole(sc.next());
            logins.add(l);
            System.out.println("Signup successful!");
        }
    }
    
    public static Login checkrole(String name) {
        Scanner sc = new Scanner(System.in);
        
        if (logins.isEmpty()) {
            return null;
        }
        
        for (Login login : logins) {
            if (name.equals(login.getUname())) {
                return validatePassword(sc, login);
            }
        }
        
        return null; // User not found
    }
    
    private static Login validatePassword(Scanner sc, Login login) {
        while (true) {
            System.out.print("\nEnter the password: ");
            String pwd = sc.next();
            try {
                if (pwd.equals(login.getPassword())) {
                    return login; // Successful login
                } else {
                    throw new invalidpassexception("Your password is incorrect. Please try again.");
                }
            } catch (invalidpassexception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static void adminInventory(Inventory inv) {
        Scanner sc = new Scanner(System.in);
        int op;
        
        do {
            System.out.print("\n1. Add 2. Update 3. Remove 4. Search 5. Show 6. Logout: ");
            op = sc.nextInt();
            
            switch (op) {
                case 1: 
                    addBook(sc, inv);
                    break;
                    
                case 2: 
                    updateBook(sc, inv);
                    break;
                    
                case 3: 
                    removeBook(sc, inv);
                    break;
                    
                case 4: 
                    searchBook(sc, inv);
                    break;
                    
                case 5: 
                    inv.show();
                    break;
                    
                case 6: 
                    System.out.println("Logging out...");
                    break;
                    
                default: 
                    System.out.println("Invalid option");
            }
        } while (op != 6);
    }
    
    private static void addBook(Scanner sc, Inventory inv) {
        Book b = new Book();
        System.out.print("Enter the Book name, Edition, ISBN, Author, Publisher, Price: ");
        b.setName(sc.next());
        b.setEdition(sc.next());
        b.setIsbn(sc.next());
        b.setAuthor(sc.next());
        b.setPublisher(sc.next());
        b.setPrice(sc.nextInt());
        inv.add(b);
        System.out.println("Book added successfully.");
    }
    
    private static void updateBook(Scanner sc, Inventory inv) {
        System.out.print("Enter the ID to update: ");
        String id = sc.next();
        Book b = inv.search(id);
        
        if (b == null) {
            System.out.println("ID not found for update.");
        } else {
            System.out.print("Enter new details (name edition ISBN author publisher price): ");
            b.setName(sc.next());
            b.setEdition(sc.next());
            b.setIsbn(sc.next());
            b.setAuthor(sc.next());
            b.setPublisher(sc.next());
            b.setPrice(sc.nextInt());
            inv.update(b);
            System.out.println("Book updated successfully.");
        }
    }
    
    private static void removeBook(Scanner sc, Inventory inv) {
        System.out.print("Enter the ID to remove: ");
        String id = sc.next();
        inv.remove(id);
        System.out.println("Book removed successfully.");
    }
    
    private static void searchBook(Scanner sc, Inventory inv) {
        System.out.print("Enter the ID to display: ");
        Book b = inv.search(sc.next());
        
        if (b == null) {
            System.out.println("The given ID does not exist.");
        } else {
            System.out.println(b);
        }
    }

    public static void customerInventory(Inventory inv) {
        Scanner sc = new Scanner(System.in);
        int op;
        
        do {
            System.out.print("\n1. Sell 2. Buy 3. Search 4. Show 5. Logout: ");
            op = sc.nextInt();
            
            switch (op) {
                case 1: 
                    sellBook(sc, inv);
                    break;
                    
                case 2: 
                    buyBook(sc, inv);
                    break;
                    
                case 3: 
                    searchBookForCustomer(sc, inv);
                    break;
                    
                case 4: 
                    inv.show();
                    break;
                    
                case 5: 
                    System.out.println("Logging out...");
                    break;
                    
                default: 
                    System.out.println("Invalid option");
            }
        } while (op != 5);
    }
    
    private static void sellBook(Scanner sc, Inventory inv) {
        Book b = new Book();
        System.out.print("Enter the Book name, Edition, ISBN, Author, Publisher, Price: ");
        b.setName(sc.next());
        b.setEdition(sc.next());
        b.setIsbn(sc.next());
        b.setAuthor(sc.next());
        b.setPublisher(sc.next());
        b.setPrice(sc.nextInt());
        inv.add(b);
        System.out.println("Book sold successfully.");
    }
    
    private static void buyBook(Scanner sc, Inventory inv) {
        System.out.print("Enter the Edition to buy: ");
        String edition = sc.next();

        Book bookToBuy = null;
        String qry = "SELECT * FROM book WHERE Edition = ?"; // Query to find the book by edition

        try (Connection con = inv.db.getDBConnection();
             PreparedStatement pstmt = con.prepareStatement(qry)) {
            
            pstmt.setString(1, edition);
            ResultSet rs = pstmt.executeQuery();

            // Check if the book exists
            if (rs.next()) {
                bookToBuy = new Book();
                bookToBuy.setId(rs.getString("ID"));
                bookToBuy.setName(rs.getString("Name"));
                bookToBuy.setEdition(rs.getString("Edition"));
                bookToBuy.setIsbn(rs.getString("ISBN"));
                bookToBuy.setPrice(rs.getInt("Price"));
                bookToBuy.setAuthor(rs.getString("Author"));
                bookToBuy.setPublisher(rs.getString("Publisher"));
                bookToBuy.setGenre(rs.getString("Genre"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // If the book is found, proceed with the buying process
        if (bookToBuy != null) {
            System.out.println("Book found:");
            System.out.println(bookToBuy); // Use the toString method from the Book class

            System.out.print("Do you want to buy this book? (yes/no): ");
            String confirm = sc.next();

            if (confirm.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for your purchase!");
            } else {
                System.out.println("Purchase canceled.");
            }
        } else {
            System.out.println("Book with edition '" + edition + "' not found.");
        }
    }

    
    private static void searchBookForCustomer(Scanner sc, Inventory inv) {
        System.out.print("Enter the Edition  to display: ");
        Book b = inv.search(sc.next());
        
        if (b != null) {
            System.out.println(b);
        } else {
            System.out.println("Book not found.");
        }
    }
}
