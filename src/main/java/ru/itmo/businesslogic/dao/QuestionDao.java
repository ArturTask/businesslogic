package ru.itmo.businesslogic.dao;

import com.oracle.tools.packager.Log;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.entities.Question;
import ru.itmo.businesslogic.entities.User;
import ru.itmo.businesslogic.jms.MyJmsLictener;
import ru.itmo.businesslogic.services.MyJmsListener;
import ru.itmo.businesslogic.services.RabbitMqSender;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Transactional
@Repository
public class QuestionDao {

    @Autowired
    private RabbitMqSender rabbitMqSender;

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
            return new QuestionDto(question.getId(), question.getCreatorId(), "", "", false, question.getTag().toString(), null,"question successfully added");
        }
        return new QuestionDto("Invalid user id");
    }


    public QuestionDto deleteQuestion (int id, String token) {
        Question question = findQuestionById(id);
        if(question==null){
            return new QuestionDto("No question with id = "+id);
        }
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

    public QuestionDto deleteQuestion (int id) {
        Question question = findQuestionById(id);
        if(question==null){
            return new QuestionDto("No question with id = "+id);
        }
        entityManager.remove(question);
        return new QuestionDto("Question with id " + id + " was removed");
    }


    public QuestionDto changeQuestionStatusToEvaluated(int id) {
        Question question = findQuestionById(id);
        if(question==null){
            return new QuestionDto("No question with id = "+id);
        }
        question.setEvaluated(true);
        entityManager.merge(question);
        return new QuestionDto("Question with id " + id + " was evaluated");
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

    public QuestionDto getQuestionsForEvaluation(String token) {
        User user = ((List<User>) entityManager.createQuery("From User as user Where user.token = '" + token + "'").getResultList()).get(0);
        List<Question> questions = (List<Question>) entityManager.createQuery("From Question as question where question.moderatorId =" + user.getId())
                .getResultList();
        return new QuestionDto(null,null,"","",true,"",questions, MyJmsListener.getMessage());
    }

    public void update(Question question) {
        entityManager.merge(question);
    }


    @Scheduled(fixedDelay = 3600000)
    public void addModeratorsToQuestions() {
//        LOGGER.info("add moderators to questions");
        List<User> moderators = (List<User>) entityManager.createQuery("From User as user Where user.role = 'ADMIN'").getResultList();
        List<Question> questions = (List<Question>) entityManager.createQuery("From Question as question Where question.moderatorId IS NULL").getResultList();

        int questionsCountForModerator = questions.size() / moderators.size();
        int remainder = questions.size() - questionsCountForModerator * moderators.size();
        int currentModeratorIndex = 0;
        int currentCount = 0;
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E MM.dd HH:mm:ss ");

        rabbitMqSender.send(formatForDateNow.format(dateNow)+" :Add moderators to questions");

        for(Question question: questions) {
            question.setModeratorId(moderators.get(currentModeratorIndex).getId());
            update(question);
            currentCount++;
            if(currentModeratorIndex < remainder) {
                if(currentCount == questionsCountForModerator + 1) {
                    currentCount = 0;
                    currentModeratorIndex++;
                }
            }
            else if(currentCount == questionsCountForModerator) {
                currentCount = 0;
                currentModeratorIndex++;
            }
        }

    }


    private static String lol = MyJmsLictener.getMessage();


}