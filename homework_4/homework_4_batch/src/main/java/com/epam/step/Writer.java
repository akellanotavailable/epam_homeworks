package com.epam.step;

import com.epam.entity.User;
import com.epam.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j
@RequiredArgsConstructor
public class Writer implements ItemWriter<User> {

    private final EmailSender emailSender;

    @Override
    public void write(List<? extends User> list) {
        for (User user : list) {
            if (user != null) {
                emailSender.send(user.getEmail(), "Balance", "balance is lower than 10$");
                log.info("mail sent " + user.getEmail());
            }
        }
    }
}
