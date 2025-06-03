package com.example.tarea13.repository;

import com.example.tarea13.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByStatus(String status);
    List<Book> findByAvailableCopiesGreaterThan(int count);
} 