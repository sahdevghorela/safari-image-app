package com.learn.springboot.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class LearningSpringBootHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {

        try {
            int responseCode = ((HttpURLConnection) new URL("http://google.com").openConnection()).getResponseCode();
            if(responseCode >= 200 && responseCode < 300){
                return Health.up().build();
            }else{
                return Health.down().withDetail("Http Status Code",responseCode).build();
            }
        } catch (java.io.IOException e) {
           return Health.down(e).build();
        }

    }
}
