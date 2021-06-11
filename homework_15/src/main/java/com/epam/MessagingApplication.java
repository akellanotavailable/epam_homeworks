package com.epam;

import com.epam.logic.Receiver;
import com.epam.logic.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagingApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MessagingApplication.class, args);
    }

    @Autowired
    private Sender sender;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception{
        sender.createMsgList();
    }

}
