package ru.itmo.businesslogic.entities;

import lombok.Data;
import ru.itmo.businesslogic.enums.Tag;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@Table(name = "questions",schema = "s274007")
@Table(name = "questions",schema = "public")
@Data
public class Question implements Serializable {
    public Question() {
    }

    public Question(int creatorId, String head, String body, boolean evaluated, Tag tag) {
        this.creatorId = creatorId;
        this.head = head;
        this.body = body;
        this.evaluated = evaluated;
        this.tag = tag;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "creator_id")
    private int creatorId;

    @Column(name="head")
    private String head;

    @Column(name="body")
    private String body;

    @Column(name = "evaluated")
    private boolean evaluated;

    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    public int getId() {
        return id;
    }

    public int getCreatorId() {
        return creatorId;
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

    public Tag getTag() {
        return tag;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }
}