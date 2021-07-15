package com.zoro;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogPrintTest {

    @Test
    public void test() {
        try {
            int a =  1/0;
        }catch (Exception e){
            log.error("exception:",e);
        }
    }
}
