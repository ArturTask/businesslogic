package ru.itmo.businesslogic.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.services.QuestionService;

@Component
public class QuestionDelegate implements JavaDelegate {

    @Autowired
    QuestionService questionService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //id head body tag
        QuestionDto questionDto = questionService.addQuestion(new QuestionDto((String) delegateExecution.getVariable("curr_token"),Integer.parseInt((String) delegateExecution.getVariable("creator_id")),(String) delegateExecution.getVariable("question_title"),(String) delegateExecution.getVariable("question_body"),"JAVA"));
        delegateExecution.setVariable("message",questionDto.getMsg());
    }
}
