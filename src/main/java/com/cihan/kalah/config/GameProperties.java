package com.cihan.kalah.config;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "kalah")
@Configuration
@ToString
public class GameProperties {

    public static int DEFAULT_PIT_COUNT;
    public static int DEFAULT_STONE_COUNT;


    public void setDefaultPitCount(int defaultPitCount) {
        DEFAULT_PIT_COUNT = defaultPitCount;
    }

    public void setDefaultStoneCount(int defaultStoneCount) {
        DEFAULT_STONE_COUNT = defaultStoneCount;
    }
}


