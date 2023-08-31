package com.project.todo.test.aopmethodaccess;

import com.project.todo.common.test.aopmethodaccess.AopService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@Slf4j
@SpringBootTest
public class AopMethodAccessTest {

    @Autowired
    AopService aopService;

    @Test
    void callPublic() {
        aopService.publicMethod();
        log.info("================================");
    }

    @Test
    void callProtected() {
        aopService.protectedMethod();
        log.info("================================");
    }

    @Test
    void callDefault() {
        aopService.defaultMethod();
        log.info("================================");
    }

    @TestConfiguration
    static class Config {

        @Bean
        public AopService aopService() {
            return new AopService();
        }
    }
}
