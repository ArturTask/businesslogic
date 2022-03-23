package ru.itmo.businesslogic.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.businesslogic.config.ProcessVariableConstraints;
import ru.itmo.businesslogic.dto.UserDto;
import ru.itmo.businesslogic.services.UserService;

@Component
public class AuthDelegate implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String login = (String) delegateExecution.getVariable(ProcessVariableConstraints.LOGIN);
        String password = (String) delegateExecution.getVariable(ProcessVariableConstraints.PASSWORD);
//        System.out.println(login);
        UserDto userDto = userService.signIn(new UserDto(login,password,"msg?"));
        boolean approved = false;
        String currentId ="";
        String currentToken ="";
        if(!userDto.getToken().equals("")){
            approved=true;
            currentId = String.valueOf(userDto.getId());
            currentToken = userDto.getToken();
        }
        delegateExecution.setVariable("is_authorised",approved);
        delegateExecution.setVariable("user_id",currentId);
        delegateExecution.setVariable("token",currentToken);
    }
}
