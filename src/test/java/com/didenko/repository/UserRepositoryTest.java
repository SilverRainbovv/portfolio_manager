package com.didenko.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;
    private static final Long USER_ID_1 = 1L;
    private static final Long NON_EXISTING_ID = 1000L;
    @Test
    void findById() {
        var user = userRepository.findById(USER_ID_1);
        Assertions.assertThat(user.isPresent()).isTrue();
    }
    @Test
    void findByNonExistingId(){
        Assertions.assertThat(userRepository.findById(NON_EXISTING_ID))
                .isEmpty();
    }
    @Test
    void findAll(){
        Assertions.assertThat(userRepository.findAll())
                .hasSize(5);
    }
    @Test
    void update(){
        var getUser = userRepository.findById(USER_ID_1);
        Assertions.assertThat(getUser).isPresent();

    }
}