package com.didenko;

import com.didenko.dao.UserRepository;
import com.didenko.entity.UserInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SpringBootApplication
public class ApplicationRunner
{
    public static void main( String[] args )
    {
      var context = SpringApplication.run(ApplicationRunner.class, args);

      var userRepository = context.getBean("userRepository", UserRepository.class);
        System.out.println();
    }
}
