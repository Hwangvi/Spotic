package com.backSpotic.backSpotic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.ssl.enabled=false"
)
class BackSpoticApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        BackSpoticApplication.main(new String[] {});
    }
}