package com.example.quizapp.service;

import com.example.quizapp.dao.request.AnswerRequest;
import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.model.Answer;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.AnswerRepository;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final JwtService jwtService;

    public String answerChecker(AnswerRequest answerRequest){
        Question question= questionRepository.findById(answerRequest.getQuestionId()).orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUESTION));
        String username = jwtService.extractUserName(answerRequest.getToken());
        Answer answer = new Answer();
        answer.setUsername(username);
        answer.setQuestionId(answerRequest.getQuestionId());
        answer.setUserAnswer(answerRequest.getUserAnswer());

        if(answer.getUserAnswer().equals(question.getRightAnswer())){
            answer.setChecker(true);
            answerRepository.customSave(answer);
            return "User " + username + ", your answer '" + answer.getUserAnswer() + "' on the question '"+question.getQuestionTitle() + "' is correct";
        }
        else{
            answer.setChecker(false);
            answerRepository.save(answer);
            return "User " + username + ", your answer '" + answer.getUserAnswer() + "' on the question '"+question.getQuestionTitle() + "' is incorrect." +
                    "\nCorrect answer: '" + question.getRightAnswer()+"'";
        }
    }


    public String countAnswersOfUser(String username) {
        return "User " + username + " has " + answerRepository.countCorrectAnswers(username) +
                " correct answers and " + answerRepository.countIncorrectAnswers(username) + " incorrect.";
    }

    public Answer findAnswerById(Long id) {
        return answerRepository.findAnswerById(id);
    }

    public String deleteAnswerById(Long id) {
        answerRepository.deleteAnswerById(id);
        return "Answer with id : " + id + " was deleted.";
    }
}
