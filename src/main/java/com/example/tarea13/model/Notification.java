package com.example.tarea13.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = true)
    private Loan loan;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "sent_at", nullable = false)
    private Timestamp sentAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public Notification() {}

    public Notification(User user, Loan loan, String message, Timestamp sentAt, String status) {
        this.user = user;
        this.loan = loan;
        this.message = message;
        this.sentAt = sentAt;
        this.status = status;
    }

    public Integer getNotificationId() { return notificationId; }
    public User getUser() { return user; }
    public Loan getLoan() { return loan; }
    public String getMessage() { return message; }
    public Timestamp getSentAt() { return sentAt; }
    public String getStatus() { return status; }

    public void setNotificationId(Integer notificationId) { this.notificationId = notificationId; }
    public void setUser(User user) { this.user = user; }
    public void setLoan(Loan loan) { this.loan = loan; }
    public void setMessage(String message) { this.message = message; }
    public void setSentAt(Timestamp sentAt) { this.sentAt = sentAt; }
    public void setStatus(String status) { this.status = status; }
} 