package ru.itmo.businesslogic.dao;

import org.springframework.stereotype.Repository;
import ru.itmo.businesslogic.dto.UserDto;
import ru.itmo.businesslogic.entities.User;
import ru.itmo.businesslogic.util.EncryptionUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Transactional()
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean isThereUserWithSuchId(int id) {
        List<User> users = (List<User>) entityManager.createQuery("From User as user where user.id =" + id ).getResultList();
        return !users.isEmpty();
    }

    public User findUserById(int id) {
        List<User> users = (List<User>) entityManager.createQuery("From User as user where user.id =" + id ).getResultList();
        if(!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public User findUser(String login, String password) throws NoSuchAlgorithmException {
        User currentUser = findByLogin(login);
        if(currentUser!=null){
//            System.out.println(users.get(0).getLogin());
            return currentUser;
//            return new UserDto( login, password, currentUser.getEmail(),currentUser.getToken(),currentUser.getRole().toString(), "");
        }
        return null;
//        return new UserDto( null, null, "");
    }

    public User findByLogin(String login) {
        List<User> users = (List<User>) entityManager.createQuery("From User as user where user.login ='" + login + "'").getResultList();
        if( !users.isEmpty()){
            return users.get(0);
        }
        else {
            return null;
        }
    }

    public boolean isThereUserWithSuchLogin(String login) {
        List<User> users = (List<User>) entityManager.createQuery("From User as user where user.login ='" + login + "'").getResultList();
        return !users.isEmpty();
    }

    public UserDto save(User user) throws NoSuchAlgorithmException {
        if(!isThereUserWithSuchLogin(user.getLogin())) {
//            System.out.println("dao ="+user.getToken());
//            user.setToken(user.getToken()); //token
            entityManager.persist(user);
            return new UserDto(user.getLogin(), user.getPassword(), user.getEmail(),user.getToken(),user.getRole().toString(), "");
        }
        return new UserDto(null, null, null, "Cant create user with same login: user '" + user.getLogin() + "' already exists ");
    }
}