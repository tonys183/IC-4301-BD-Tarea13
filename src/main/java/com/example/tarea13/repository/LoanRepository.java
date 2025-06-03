package com.example.tarea13.repository;

import com.example.tarea13.model.Loan;
import com.example.tarea13.model.User;
import com.example.tarea13.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUser(User user);
    List<Loan> findByBook(Book book);
    List<Loan> findByStatus(String status);
    List<Loan> findByBookAndStatus(Book book, String status);
    List<Loan> findByUserAndStatus(User user, String status);
} 