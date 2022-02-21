package ru.itmo.businesslogic.controllers;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.businesslogic.dao.QuestionDao;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.services.QuestionService;

import javax.validation.Valid;

@RestController
@RequestMapping("questions/")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PutMapping("add")
    public QuestionDto addQuestion(@RequestBody QuestionDto question){
        return questionService.addQuestion(question);
    }

    @GetMapping(path = "user/{userId}")
    public QuestionDto getForUser(@PathVariable int userId) {
        return questionService.getForUser(userId);
    }

    @DeleteMapping("delete")
    public QuestionDto deleteQuestion(@RequestBody QuestionDto questionDto){
        try {
            return questionService.deleteQuestion(questionDto.getId(), questionDto.getToken());
        }
        catch (Exception e) {
            return new QuestionDto("Bad request: no id");
        }
    }

    @PostMapping("change_status")
    public QuestionDto changeQuestionStatus(@RequestBody QuestionDto questionDto) {
        return questionService.changeQuestionStatus(questionDto.getId(), questionDto.isValid());
    }


    @GetMapping("all")
    public QuestionDto getAll() {
        return questionService.getAll();
    }



}