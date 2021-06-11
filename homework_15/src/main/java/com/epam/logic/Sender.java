package com.epam.logic;

import com.epam.config.ActiveMQConfig;
import com.epam.entity.Message;
import com.epam.entity.MessageReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Sender {

    public static final Integer MSG_COUNTER = 10;

    @Autowired
    private JmsTemplate jmsTemplate;

    public List<Message> messageList = new ArrayList<>();

    private void sendMsgList(Message message) {
        jmsTemplate.convertAndSend(ActiveMQConfig.RECEIVE, message);
        System.out.println("Send " + message.toString());
    }

    public void createMsgList() {
        for (int i = 0; i < MSG_COUNTER; i++) {
            messageList.add(new Message(randomLong(), randomInt(), randomInt()));
            sendMsgList(messageList.get(i));
        }
    }

    private Integer randomInt() {
        return (int) (Math.random() * 100);
    }

    private Long randomLong() {
        return Double.valueOf(Math.random() * 10000).longValue();
    }

    @JmsListener(destination = ActiveMQConfig.REPLY)
    public void receiveReplyMsg(@Payload MessageReply messageReply) {
        if (getResult(messageReply)) {
            System.out.println("Message replied " + messageReply.toString());
        } else {
            System.out.println("Error");
        }
    }

    public boolean getResult(MessageReply messageReply) {
        return messageList.stream()
                .filter(message -> message.getId().equals(messageReply.getId()))
                .anyMatch(message -> (message.getFirstValue() + message.getSecondValue()) == messageReply.getResult());
    }
}
