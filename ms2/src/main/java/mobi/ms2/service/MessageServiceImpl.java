package mobi.ms2.service;

import lombok.extern.slf4j.Slf4j;
import mobi.ms2.enums.Ms;
import mobi.ms2.model.Message;
import mobi.ms2.model.ReceivedMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    RestTemplate restTemplate;

    public MessageServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void initialProcess() {
        //The initial message
        Message initialMessage = new Message(UUID.randomUUID().toString(),"MS2", Ms.MS2, null,0);

        //sending message
        sendingProcess(initialMessage, "lb://DISPATCHER/process");
    }

    @Override
    public void sendingMessage(Message message) {
        if(message.getCounter() < 5)
        {
            //Before sending message setting the courant source
            message.setSource(Ms.MS2);

            //Before sending message generating a new Id
            message.setId(UUID.randomUUID().toString());

            ReceivedMessage receivedMessage = new ReceivedMessage(UUID.randomUUID().toString(),"Well received from source : "+message.getSource()+" to destination : "+Ms.MS2,message.getSource(),message.getDestination());

            restTemplate.postForObject("lb://DISPATCHER/reaching",receivedMessage,ReceivedMessage.class);

            //sending message
            sendingProcess(message, "lb://DISPATCHER/process");

        }
    }

    @Override
    public void receivingMessage(ReceivedMessage receivedMessage) {
        log.info(receivedMessage.getReachingMsg());
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
