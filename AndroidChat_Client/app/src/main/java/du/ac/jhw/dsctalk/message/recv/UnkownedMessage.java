package du.ac.jhw.dsctalk.message.recv;

import du.ac.jhw.dsctalk.client.MessageAcceptor;
import du.ac.jhw.dsctalk.message.RecvMessage;

public class UnkownedMessage extends RecvMessage {
    public UnkownedMessage(int messageType, int messageSize) {
        super(messageType, messageSize);
    }

    @Override
    public void execute(MessageAcceptor acceptor) {

    }
}
