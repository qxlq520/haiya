package com.haiya.user;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    classes = UserServiceApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@TestPropertySource(locations = "classpath:application.yml")
@Disabled
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}