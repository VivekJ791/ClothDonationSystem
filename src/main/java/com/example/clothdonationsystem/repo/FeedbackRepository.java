package com.example.clothdonationsystem.repo;

import com.example.clothdonationsystem.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
