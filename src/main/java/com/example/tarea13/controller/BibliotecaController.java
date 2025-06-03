package com.example.tarea13.controller;

import com.example.tarea13.model.Book;
import com.example.tarea13.model.Loan;
import com.example.tarea13.model.User;
import com.example.tarea13.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping("/libros")
    public ResponseEntity<List<Book>> getLibros(@RequestParam(required = false) String disponibilidad) {
        List<Book> books;
        if ("disponible".equalsIgnoreCase(disponibilidad)) { books = bibliotecaService.getAvailableBooks(); } 
        else { books = bibliotecaService.getAllBooks(); }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<User>> getUsuarios() {
        List<User> users = bibliotecaService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/prestamos")
    public ResponseEntity<?> solicitarPrestamo(@RequestBody PrestamoRequest prestamoRequest) {
        try {
            Loan loan = bibliotecaService.realizarPrestamo(prestamoRequest.getBookId(), prestamoRequest.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(loan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    static class PrestamoRequest {
        private Integer bookId;
        private Integer userId;

        public Integer getBookId() { return bookId; }
        public Integer getUserId() { return userId; }

        public void setBookId(Integer bookId) { this.bookId = bookId; }
        public void setUserId(Integer userId) { this.userId = userId;}
    }
} 