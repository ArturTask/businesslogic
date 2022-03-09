package ru.itmo.businesslogic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.services.QuestionService;
import ru.itmo.businesslogic.services.RabbitMqSender;

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
        return questionService.changeQuestionStatus(questionDto.getId(), questionDto.isValid(),questionDto.getToken());
    }


    @GetMapping("all")
    public QuestionDto getAll() {
        return questionService.getAll();
    }

    @GetMapping("get_questions_for_evaluation")
    public QuestionDto getQuestions(@RequestBody QuestionDto questionDto) {
        return questionService.getQuestionsForEvaluation(questionDto.getToken());
    }



}