package ru.itmo.businesslogic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.businesslogic.dao.UserDao;
import ru.itmo.businesslogic.dto.UserDto;
import ru.itmo.businesslogic.entities.User;
import ru.itmo.businesslogic.enums.Role;
import ru.itmo.businesslogic.util.EncryptionUtil;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserDto registr(UserDto userDto){
        try {
            if (userDto.getLogin() == null || userDto.getPassword() == null || userDto.getEmail() == null) {
                return new UserDto("Bad request some parameters are missing");
            }
            UserDto currentUser = userDao.save(new User(userDto.getLogin(), userDto.getPassword(), userDto.getEmail(), Role.ROLE_USER));
            if (currentUser.getLogin() != null) {
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

    public UserDto signIn(UserDto userDto){
        try {
            UserDto currentUser = userDao.findUser(userDto.getLogin(), userDto.getPassword());
            if(currentUser.getLogin()==null){
                currentUser.setMsg("Login failed");
                return currentUser;
            }
            else {
                currentUser.setMsg("Login Success");
                return currentUser;
            }
        }
        catch (NoSuchAlgorithmException e){
            return null;
        }

    }

}
