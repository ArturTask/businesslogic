package ru.itmo.businesslogic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import ru.itmo.businesslogic.dao.UserDao;
import ru.itmo.businesslogic.entities.User;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public MyUserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userDao.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

}
