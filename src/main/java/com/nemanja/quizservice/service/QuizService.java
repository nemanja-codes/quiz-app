package com.nemanja.quizservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.nemanja.quizservice.dao.QuestionDao;
import com.nemanja.quizservice.dao.QuizDao;
import com.nemanja.quizservice.feign.QuizInterface;
//import com.nemanja.quizservice.model.Question;
import com.nemanja.quizservice.model.QuestionWrapper;
import com.nemanja.quizservice.model.Quiz;
import com.nemanja.quizservice.model.Response;

@Service
public class QuizService {
	@Autowired
	QuizDao quizDao;
	@Autowired
	QuizInterface quizInterface;
	//@Autowired
	//QuestionDao questionDao;
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		// call the generate URL - RestTemplate
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionIds(questions);
		quizDao.save(quiz);
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
		
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		
		  Quiz quiz = quizDao.findById(id).get();
		  List<Integer> questionIds = quiz.getQuestionIds();
		  
		  ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
		  
		  
		return questions;
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		/*
		 * Quiz quiz = quizDao.findById(id).get(); List<Question> questions =
		 * quiz.getQuestions();
		 */
		int right = 0;
		/*
		 * int i = 0; for(Response r : responses) {
		 * if(r.getResponse().equals(questions.get(i).getRightAnswer())) right++; i++; }
		 */
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
	
	
}
