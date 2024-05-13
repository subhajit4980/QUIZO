package com.subhajit.Questionservice.Service;

import com.subhajit.Questionservice.Dao.QuestionDao;
import com.subhajit.Questionservice.Model.Question;
import com.subhajit.Questionservice.Model.QuestionWrapper;
import com.subhajit.Questionservice.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions()  {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public  ResponseEntity<String>  updateQuestion(Question question) {
        Question updateQuestion=questionDao.findById(question.getId()).get();
        if(question.getQuestionTitle()!=null)
            updateQuestion.setQuestionTitle(question.getQuestionTitle());
        if(question.getCategory()!=null)
            updateQuestion.setCategory(question.getCategory());
        if(question.getDifficultylevel()!=null)
            updateQuestion.setDifficultylevel(question.getDifficultylevel());
        if(question.getOption1()!=null)
            updateQuestion.setOption1(question.getOption1());
        if(question.getOption2()!=null)
            updateQuestion.setOption2(question.getOption2());
        if(question.getOption3()!=null)
            updateQuestion.setOption3(question.getOption3());
        if(question.getOption4()!=null)
            updateQuestion.setOption4(question.getOption4());
        if(question.getRightAnswer()!=null)
            updateQuestion.setRightAnswer(question.getRightAnswer());
        if(question.getTopic()!=null)
            updateQuestion.setTopic(question.getTopic());
        questionDao.save(updateQuestion);
        return new ResponseEntity<>("question updated",HttpStatus.OK);
    }
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numOfQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numOfQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = questionIds.stream()
                .map(id -> questionDao.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        for(Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper(question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int correct = 0;
        for(Response response : responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
                correct++;
        }
        return new ResponseEntity<>(correct, HttpStatus.OK);
    }
}
