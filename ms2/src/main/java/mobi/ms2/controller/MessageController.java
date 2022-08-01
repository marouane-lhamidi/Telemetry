package mobi.ms2.controller;

import mobi.ms2.model.Message;
import mobi.ms2.model.ReceivedMessage;
import mobi.ms2.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageController {

    MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/process")
    void initialProcess()
    {
        messageService.initialProcess();
    }

    @PostMapping("/process")
    void processMessage(@RequestBody Message message)
    {
        messageService.sendingMessage(message);
    }

    @PostMapping("/reaching")
    void reachingMessage(@RequestBody ReceivedMessage receivedMessage){
        messageService.receivingMessage(receivedMessage);
    }
}
