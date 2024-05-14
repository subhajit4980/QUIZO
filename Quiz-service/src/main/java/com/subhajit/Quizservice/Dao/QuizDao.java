package com.subhajit.Quizservice.Dao;

import com.subhajit.Quizservice.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
