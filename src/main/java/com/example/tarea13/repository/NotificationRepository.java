package com.example.tarea13.repository;

import com.example.tarea13.model.Notification;
import com.example.tarea13.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndStatus(User user, String status);
} 