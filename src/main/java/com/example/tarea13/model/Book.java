package com.example.tarea13.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "author", nullable = false, length = 100)
    private String author;

    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public Book() {}
    public Book(String title, String author, String isbn, Integer availableCopies, Integer totalCopies, String status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.status = status;
    }

    public Integer getBookId() { return bookId; } 
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public Integer getAvailableCopies() { return availableCopies; }
    public Integer getTotalCopies() { return totalCopies; }
    public String getStatus() { return status; }

    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }
    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
    public void setStatus(String status) { this.status = status; }
} 