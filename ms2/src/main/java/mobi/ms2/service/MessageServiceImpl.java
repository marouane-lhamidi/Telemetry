package mobi.ms2.service;

import lombok.extern.slf4j.Slf4j;
import mobi.ms2.enums.Ms;
import mobi.ms2.model.Message;
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
        Message initialMessage = new Message(UUID.randomUUID().toString(),"MS2", Ms.MS2,0);

        //sending message
        restTemplate.postForObject("lb://DISPATCHER/process",initialMessage,Message.class);
    }

    @Override
    public void sendingMessage(Message message) {
        if(message.getCounter() < 5)
        {
            //Before sending message setting the courant source
            message.setSource(Ms.MS2);

            //Before sending message generating a new Id
            message.setId(UUID.randomUUID().toString());

            //sending message
            restTemplate.postForObject("lb://DISPATCHER/process",message,Message.class);

        }
    }


}
