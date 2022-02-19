package ru.itmo.businesslogic.dto;

import lombok.Builder;

public class UserDto {

    public UserDto() {
    }

    public UserDto(Integer id, String login, String password, String email, String msg) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.msg = msg;
    }

    public UserDto(String login, String password, String email, String msg) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.msg = msg;
    }

    public UserDto(String login, String password, String email, String role, String msg) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.msg = msg;
    }

    public UserDto(String login, String password, String email, String role, String token, String msg) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.token = token;
        this.msg = msg;
    }

    public UserDto(String login, String password, String msg) {
        this.login = login;
        this.password = password;
        this.msg = msg;
    }

    public UserDto(String msg) {
        this.msg = msg;
    }

    private Integer id;

    private String login;

    private String password;

    private String email;

    private String role;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String msg;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addMsg(String msg){
        this.msg+=msg;
    }

    public String getRole() {
        return this.role;
    }

}
