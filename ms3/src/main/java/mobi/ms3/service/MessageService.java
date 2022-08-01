package mobi.ms3.service;

import mobi.ms3.model.Message;
import mobi.ms3.model.ReceivedMessage;

public interface MessageService {
    void initialProcess();
    void sendingMessage(Message message);
    public void receivingMessage(ReceivedMessage receivedMessage);
}
