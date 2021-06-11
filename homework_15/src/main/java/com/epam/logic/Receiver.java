package com.epam.logic;

import com.epam.config.ActiveMQConfig;
import com.epam.entity.Message;
import com.epam.entity.MessageReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Receiver {

    @Autowired
    private JmsTemplate jmsTemplate;

    private List<Message> messageList = new ArrayList<>();

    @JmsListener(destination = ActiveMQConfig.RECEIVE)
    public void receiveMsg(@Payload Message message){
        messageList.add(message);
        System.out.println("adding received messages...");
        if(messageList.size() == Sender.MSG_COUNTER){
            sendResult();
        }
    }

    private void sendResult(){
        messageList.forEach(
                message -> jmsTemplate.convertAndSend(
                        ActiveMQConfig.REPLY,
                        new MessageReply(
                                message.getId(),
                                message.getFirstValue() + message.getSecondValue()
                        )
                )
        );
    }
}
