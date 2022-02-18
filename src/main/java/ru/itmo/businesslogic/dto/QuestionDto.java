package ru.itmo.businesslogic.dto;

import lombok.Builder;
import ru.itmo.businesslogic.entities.Question;
import ru.itmo.businesslogic.entities.User;
import ru.itmo.businesslogic.enums.Tag;

import javax.persistence.*;
import java.util.List;


public class QuestionDto {

    public QuestionDto() {
    }

    public QuestionDto(Integer id, Integer creatorId, String head, String body, boolean evaluated, String tag, List<Question> questionsOfParticularUser) {
        this.id = id;
        this.creatorId = creatorId;
        this.head = head;
        this.body = body;
        this.evaluated = evaluated;
        this.tag = tag;
        this.questionsOfParticularUser=questionsOfParticularUser;
    }

    public QuestionDto(Integer id, Integer creatorId, String head, String body, boolean evaluated, String tag, List<Question> questionsOfParticularUser, String msg) {
        this.id = id;
        this.creatorId = creatorId;
        this.head = head;
        this.body = body;
        this.evaluated = evaluated;
        this.tag = tag;
        this.questionsOfParticularUser=questionsOfParticularUser;
        this.msg = msg;
    }

    public QuestionDto(Integer creatorId, String head, String body, boolean evaluated, String tag) {
        this.creatorId = creatorId;
        this.head = head;
        this.body = body;
        this.evaluated = evaluated;
        this.tag = tag;

    }


    public QuestionDto(String msg) {
        this.msg = msg;
    }

    private String token;

    private Integer id;

    private Integer creatorId;

    private User creator;

    private String head;

    private String body;

    private boolean evaluated;

    private String tag;

    private List<Question> questionsOfParticularUser;

    private String msg;

    public Integer getId() {
        return id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public User getCreator() {
        return creator;
    }

    public String getHead() {
        return head;
    }

    public String getBody() {
        return body;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public String getTag() {
        return tag;
    }

    public List<Question> getQuestionsOfParticularUser() {
        return questionsOfParticularUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void addMsg(String msg) {
        this.msg += msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}