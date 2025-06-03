package com.example.tarea13.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Integer loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "loan_date", nullable = false)
    private Timestamp loanDate;

    @Column(name = "expected_return_date", nullable = false)
    private Timestamp expectedReturnDate;

    @Column(name = "actual_return_date")
    private Timestamp actualReturnDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public Loan() {}

    public Loan(User user, Book book, Timestamp loanDate, Timestamp expectedReturnDate, String status) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = status;
    }

    public Integer getLoanId() { return loanId; }
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public Timestamp getLoanDate() { return loanDate; }
    public Timestamp getExpectedReturnDate() { return expectedReturnDate; }
    public Timestamp getActualReturnDate() { return actualReturnDate; }
    public String getStatus() { return status; }

    public void setLoanId(Integer loanId) { this.loanId = loanId; }
    public void setUser(User user) { this.user = user;}
    public void setBook(Book book) { this.book = book; }
    public void setLoanDate(Timestamp loanDate) { this.loanDate = loanDate; }
    public void setExpectedReturnDate(Timestamp expectedReturnDate) { this.expectedReturnDate = expectedReturnDate; }
    public void setActualReturnDate(Timestamp actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    public void setStatus(String status) { this.status = status;}
} 