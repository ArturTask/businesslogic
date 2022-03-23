package ru.itmo.businesslogic.config;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProcessVariableConstraints {

    String LOGIN = "login";
    String PASSWORD = "password";
}
