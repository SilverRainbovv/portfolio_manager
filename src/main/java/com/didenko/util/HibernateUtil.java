package com.didenko.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory(){
        Configuration configuration = buildConfiguration();

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        return sessionFactory;
    }


    private static Configuration buildConfiguration(){
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();

        return configuration;
    }

}
