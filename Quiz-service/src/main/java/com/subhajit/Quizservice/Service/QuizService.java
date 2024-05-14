package com.subhajit.Quizservice.Service;



import com.subhajit.Quizservice.Dao.QuizDao;
import com.subhajit.Quizservice.Feign.QuizInterface;
import com.subhajit.Quizservice.Model.QuestionWrapper;
import com.subhajit.Quizservice.Model.Quiz;
import com.subhajit.Quizservice.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz Successfully Created", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;

    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }

    public ResponseEntity<List<Quiz>> getQuizList() {
        List<Quiz> quizList=quizDao.findAll();
        return ResponseEntity.ok(quizList);
    }
}
