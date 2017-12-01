package com.yx.test.log;

import org.apache.log4j.Logger;

/**
 * User: LiWenC
 * Date: 16-10-14
 */
public class LoggerTest {
    private static Logger log = Logger.getLogger(LoggerTest.class);

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            log.error("hello error" + i);
            log.info("hello info" + i);
        }
    }
}
