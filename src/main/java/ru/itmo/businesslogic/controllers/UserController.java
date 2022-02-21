package ru.itmo.businesslogic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.itmo.businesslogic.dao.UserDao;
import ru.itmo.businesslogic.dto.QuestionDto;
import ru.itmo.businesslogic.dto.UserDto;
import ru.itmo.businesslogic.security.JwtProvider;
import ru.itmo.businesslogic.services.QuestionService;
import ru.itmo.businesslogic.services.UserService;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService userService;


    @PostMapping(path="login")
    public UserDto doSignIn(@RequestBody UserDto userDto) {
        try {
            return userService.signIn(userDto);
        }
        catch (Exception e){
            return new UserDto(userDto.getLogin(), "", "", "", "", "Transaction rollback Login failed");
        }
    }

    @PostMapping("reg")
    public UserDto doSignUp(@RequestBody UserDto userDto){
//        final String token = jwtProvider.generateToken(userDto.getLogin());
//        userDto.setToken(token);
        return userService.registr(userDto);
    }

}
