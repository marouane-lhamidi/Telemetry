package mobi.ms1.service;

import mobi.ms1.model.Message;
import mobi.ms1.model.ReceivedMessage;

public interface MessageService {
    void initialProcess();
    void sendingMessage(Message message);
    public void receivingMessage(ReceivedMessage receivedMessage);
}
