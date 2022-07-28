package mobi.ms2.service;

import mobi.ms2.model.Message;

public interface MessageService {
    void initialProcess();
    void sendingMessage(Message message);
}
