package com.richmillionaire.richmillionaire.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory(@Autowired EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Factory is not a Hibernate factory");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}

