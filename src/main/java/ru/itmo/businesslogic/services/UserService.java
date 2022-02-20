package ru.itmo.businesslogic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.businesslogic.dao.UserDao;
import ru.itmo.businesslogic.dto.UserDto;
import ru.itmo.businesslogic.entities.User;
import ru.itmo.businesslogic.enums.Role;
import ru.itmo.businesslogic.security.JwtProvider;
import ru.itmo.businesslogic.util.EncryptionUtil;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDao userDao;

    public UserDto registr(UserDto userDto){
        try {
            if (userDto.getLogin() == null || userDto.getPassword() == null || userDto.getEmail() == null) {
                return new UserDto("Bad request some parameters are missing");
            }
            UserDto currentUser = userDao.save(new User(userDto.getLogin(), passwordEncoder.encode(userDto.getPassword()), userDto.getEmail(), userDto.getToken(), Role.ROLE_USER));
            if (currentUser.getLogin() != null) {
                currentUser.setPassword("");
                currentUser.setMsg("Registration success");
                return currentUser;
            } else {
                currentUser.addMsg(" Registration failed");
                return currentUser;
            }
        }
        catch (NoSuchAlgorithmException e){
            return null;
        }


    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {NoSuchAlgorithmException.class,NullPointerException.class})
    public UserDto signIn(UserDto userDto){
        try {
            User currentUser = userDao.findUser(userDto.getLogin(), userDto.getPassword());
//            if(currentUser==null){
//                return new UserDto("Login failed");
//            }
//            else {
            try {
//                userDao.save(new User("1","1","1")); //testTransaction
                if (passwordEncoder.matches(userDto.getPassword(), currentUser.getPassword())) {
                    String token = jwtProvider.generateToken(userDto.getLogin());
                    currentUser.setToken(token);
                    userDao.update(currentUser);
                    return new UserDto(userDto.getLogin(), "", currentUser.getRole().toString(), currentUser.getEmail(), token, "Login Success");
                } else {
                    return new UserDto(userDto.getLogin(), "", "", "", "", "Wrong password");
                }
            }
            catch (NullPointerException e){
//                throw e;
                return new UserDto(userDto.getLogin(), "", "", "", "", "Login failed");
            }
//            }
        }
        catch (NoSuchAlgorithmException e){
            return null;
        }

    }

}
