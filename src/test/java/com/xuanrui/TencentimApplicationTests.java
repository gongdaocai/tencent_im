package com.xuanrui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TencentimApplicationTests {

    @Test
    public void contextLoads() {

        System.out.println(LocalDateTime.now().minusWeeks(5).toEpochSecond(ZoneOffset.of("+8")));
    }

}
