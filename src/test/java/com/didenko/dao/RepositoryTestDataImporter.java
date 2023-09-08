package com.didenko.dao;

import com.didenko.entity.User;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RepositoryTestDataImporter {

//    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//
//    public static void importData() {
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        User johnGold = User.builder().email("john@gmail.com").password("gold1234").build();
//        User jackGreen = User.builder().email("jack@gmail.com").password("green123213123").build();
//        User swenGrim = User.builder().email("grim@gmail.com").password("swn123456").build();
//
//        session.persist(johnGold);
//        session.persist(jackGreen);
//        session.persist(swenGrim);
//
//
//        session.getTransaction().commit();

//    }

}
