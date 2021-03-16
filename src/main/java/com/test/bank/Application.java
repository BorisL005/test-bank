package com.test.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
@SpringBootApplication
@EnableMapRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
