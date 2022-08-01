package mobi.dispatcher.service;

import lombok.extern.slf4j.Slf4j;
import mobi.dispatcher.enums.Ms;
import mobi.dispatcher.model.Message;
import mobi.dispatcher.model.ReceivedMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    RestTemplate restTemplate;

    public MessageServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendingMessage(Message message) {
        if(message.getCounter() < 5)
        {
            int randomNumber2 = (int) (Math.random() *3);
            Ms dst = Ms.values()[randomNumber2];
            //Before sending message setting the courant source
            message.setDestination(dst);

            String newBody = message.getBody().concat("-> "+dst);

            Message messageToBeSent = new Message(message.getId(),newBody,message.getSource(),dst,message.getCounter()+1);

            log.info("*******counter : {} | message with id : {} and body : {} | from source : {} | next destination {}",messageToBeSent.getCounter(),messageToBeSent.getId(),messageToBeSent.getBody(),message.getSource().toString(),dst);

            //Before sending message generating a new Id
            message.setId(UUID.randomUUID().toString());

            //sending message
            sendingProcess(messageToBeSent, "lb://"+messageToBeSent.getDestination().toString()+"/process");

        }
    }

    @Override
    public void receivingMessage(ReceivedMessage receivedMessage) {
        log.info(receivedMessage.getReachingMsg());

        try {
            restTemplate.postForObject("lb://"+receivedMessage.getSource().toString()+"/reaching",receivedMessage,ReceivedMessage.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public void sendingProcess(Message message, String destination){
        //Exception to insure the sending of the message to the destination
        try {
            //sending message
            restTemplate.postForObject(destination,message,Message.class);
        }catch (Exception e){
            log.info(e.toString());
        }
    }


}
