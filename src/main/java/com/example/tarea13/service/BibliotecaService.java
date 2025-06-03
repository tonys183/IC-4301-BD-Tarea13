package com.example.tarea13.service;

import com.example.tarea13.model.Book;
import com.example.tarea13.model.Loan;
import com.example.tarea13.model.User;
import com.example.tarea13.model.Notification;
import com.example.tarea13.repository.BookRepository;
import com.example.tarea13.repository.LoanRepository;
import com.example.tarea13.repository.UserRepository;
import com.example.tarea13.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final NotificationRepository notificationRepository;
    private static final String BOOK_STATUS_AVAILABLE = "AVAILABLE";
    private static final String BOOK_STATUS_BORROWED = "BORROWED";
    private static final String LOAN_STATUS_ACTIVE = "ACTIVE";
    private static final String NOTIFICATION_STATUS_SENT = "SENT";

    @Autowired
    public BibliotecaService(UserRepository userRepository, BookRepository bookRepository, LoanRepository loanRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public List<Book> getAvailableBooks() { return bookRepository.findByAvailableCopiesGreaterThan(0); }
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Transactional
    public Loan realizarPrestamo(Integer bookId, Integer userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + userId + " no encontrado");
        }
        User user = userOptional.get();

        // 1. Libro disponible
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) { throw new Exception("Libro con ID " + bookId + " no encontrado"); }
        Book book = bookOptional.get();
        if (book.getAvailableCopies() <= 0 || !BOOK_STATUS_AVAILABLE.equalsIgnoreCase(book.getStatus())) {
            throw new Exception("El libro '" + book.getTitle() + "' no está disponible para préstamo");
        }

        // 2. Registrar préstamo
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(Timestamp.from(Instant.now()));
        loan.setExpectedReturnDate(Timestamp.from(Instant.now().plus(14, ChronoUnit.DAYS)));
        loan.setStatus(LOAN_STATUS_ACTIVE);
        Loan savedLoan = loanRepository.save(loan);

    
        // ----- SIMULACIÓN ERROR PARA ROLLBACK -----
        if (bookId == 1) { throw new RuntimeException("Error para probar el rollback"); }
        

        // 3. Actualizar inventario
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        if (book.getAvailableCopies() == 0) { book.setStatus(BOOK_STATUS_BORROWED); }
        bookRepository.save(book);

        // 4. Enviar notificación
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setLoan(savedLoan);
        notification.setMessage("Solicitado el préstamo del libro: '" + book.getTitle() + "'. Fecha de devolución esperada: " + savedLoan.getExpectedReturnDate());
        notification.setSentAt(Timestamp.from(Instant.now()));
        notification.setStatus(NOTIFICATION_STATUS_SENT);
        notificationRepository.save(notification);

        return savedLoan;
    }
} 