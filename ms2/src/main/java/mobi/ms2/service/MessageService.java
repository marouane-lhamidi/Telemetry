package mobi.ms2.service;

import mobi.ms2.model.Message;
import mobi.ms2.model.ReceivedMessage;

public interface MessageService {
    void initialProcess();
    void sendingMessage(Message message);
    public void receivingMessage(ReceivedMessage receivedMessage);
}
