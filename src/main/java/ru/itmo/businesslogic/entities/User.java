package ru.itmo.businesslogic.entities;

import lombok.Data;
import ru.itmo.businesslogic.enums.Role;

import javax.persistence.*;
import java.io.Serializable;


@Entity
//@Table(name="users",schema = "s274007")
@Table(name="users",schema = "public")
@Data
public class User implements Serializable {

    public User(){
    }
    public User(String log, String pas, String email){
        this.login=log;
        this.password = pas;
        this.email=email;
    }

    public User(String login, String password, String email,String token, Role role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
