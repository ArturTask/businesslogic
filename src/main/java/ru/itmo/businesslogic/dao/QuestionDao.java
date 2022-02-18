package ru.itmo.businesslogic.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.entities.Question;
import ru.itmo.businesslogic.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class QuestionDao {

    @Autowired
    UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    public Question findQuestionById(int id){
        return entityManager.find(Question.class,id);
    }

    public QuestionDto save(Question question, String token) {
        String userToken = userDao.findUserById(question.getCreatorId()).getToken();
        if(token.equals(userToken)) {
            entityManager.persist(question);
            entityManager.flush(); //to get generated id
            // send id , creators id, evaluated=false and tags to client
            return new QuestionDto(question.getId(), question.getCreatorId(), "", "", false, question.getTag().toString(), null);
        }
        return new QuestionDto("Invalid user id");
    }


    public QuestionDto deleteQuestion (int id, String token) {
        Question question = findQuestionById(id);
        System.out.println(question.toString());
        if(userDao.isThereUserWithSuchId(question.getCreatorId())) {
            User owner = userDao.findUserById(question.getCreatorId());
            if (owner.getToken().equals(token)) {
                entityManager.remove(question);
                return new QuestionDto(question.getId(), question.getCreatorId(), question.getHead(), question.getBody(), true, question.getTag().toString(), null);
            }
            return new QuestionDto("It's not your question");
        }
        return new QuestionDto("Invalid user id");
    }

    public QuestionDto getQuestionsByUserId(int userId) {
        if(userDao.isThereUserWithSuchId(userId)){
            List<Question> questions = (List<Question>) entityManager.createQuery("From Question as question where question.creatorId ='" + userId + "'").getResultList();
            return new QuestionDto(null,userId,"","",true,"",questions,"Sucess");
        }
        else {
            return new QuestionDto(null,userId,"","",true,"",null,null);
        }

    }

    public QuestionDto getAllQuestions(){
        List<Question> questions = (List<Question>) entityManager.createQuery("From Question as q").getResultList();
        return new QuestionDto(null,null,"","",true,"",questions);
    }





}