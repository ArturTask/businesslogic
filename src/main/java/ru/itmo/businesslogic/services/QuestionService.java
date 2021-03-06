package ru.itmo.businesslogic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.businesslogic.dao.QuestionDao;
import ru.itmo.businesslogic.dao.UserDao;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.entities.Question;
import ru.itmo.businesslogic.enums.Tag;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    public QuestionDto addQuestion(QuestionDto questionDto){

        try {
            return questionDao.save(new Question(questionDto.getCreatorId(), questionDto.getHead(), questionDto.getBody(), false, Tag.valueOf(questionDto.getTag())), questionDto.getToken());
        }
        catch (NullPointerException e){
            return new QuestionDto("Bad request missing some of the parameters");
        }
    }

    public QuestionDto getForUser(int userID){
        QuestionDto questionDto =  questionDao.getQuestionsByUserId(userID);
        if(questionDto.getMsg()==null){
            questionDto.setMsg("Failed to find user with that id: " + userID);
            return questionDto;
        }
        questionDto.setMsg("Success");
        return questionDto;
    }

    public QuestionDto deleteQuestion(Integer questionId, String token){
        if(questionId==null)
        {
            return new QuestionDto("Bad request no id found");
        }
        return questionDao.deleteQuestion(questionId, token);

    }

    public QuestionDto getAll(){
        return questionDao.getAllQuestions();
    }

    public QuestionDto getQuestionsForEvaluation(String token) {
        return questionDao.getQuestionsForEvaluation(token);
    }



    public QuestionDto changeQuestionStatus(Integer questionId, boolean isValid, String token) {
        if(questionId==null)
        {
            return new QuestionDto("Bad request no id found");
        }
        Question question = questionDao.findQuestionById(questionId);
        if(question==null){
            return new QuestionDto("No question with id = "+questionId);
        }
        if(!userDao.findUserById(question.getModeratorId()).getToken().equals(token)) {
            return new QuestionDto("You can't evaluate this question");
        }
        if(!isValid) {
            return questionDao.deleteQuestion(questionId);
        }
        return questionDao.changeQuestionStatusToEvaluated(questionId);
    }



}