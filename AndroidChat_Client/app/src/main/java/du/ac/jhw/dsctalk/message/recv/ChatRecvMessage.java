package du.ac.jhw.dsctalk.message.recv;

import java.io.DataInputStream;
import java.io.IOException;

import du.ac.jhw.dsctalk.client.MessageAcceptor;
import du.ac.jhw.dsctalk.message.RecvMessage;

public class ChatRecvMessage extends RecvMessage {

    private String userID;
    private String destID;
    private String message;

    public ChatRecvMessage(DataInputStream input, int messageSize) throws IOException {
        super(chat, messageSize);
        userID = readString(input, userIDSize).trim();
        destID = readString(input, userIDSize).trim();
        message = readString(input,messageSize - (userIDSize * 2));
    }

    public String getUserID(){
        return userID;
    }

    public String getDestID(){
        return destID;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public void execute(MessageAcceptor acceptor) {
        acceptor.accept(this);
    }
}
