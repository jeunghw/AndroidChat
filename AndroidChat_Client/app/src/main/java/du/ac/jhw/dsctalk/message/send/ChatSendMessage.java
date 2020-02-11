package du.ac.jhw.dsctalk.message.send;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TooManyListenersException;

import du.ac.jhw.dsctalk.message.SendMessage;

public class ChatSendMessage extends SendMessage {

    private String userID;
    private String destID;
    private String message;

    public ChatSendMessage(String userID, String destID, String message) {
        super(chat, (userIDSize * 2) + message.getBytes().length);
        this.userID = userID;
        this.destID = destID;
        this.message = message;

        for(int i=userID.getBytes().length-1; i < userIDSize-1; i++) {
            this.userID = this.userID + "\0";
        }
        for(int i=destID.getBytes().length-1; i < userIDSize-1; i++) {
            this.destID = this.destID + "\0";
        }
    }

    @Override
    public void sending(DataOutputStream outputStream) throws IOException {
        sendingHeader(outputStream);
        outputStream.write(userID.getBytes());
        outputStream.write(destID.getBytes());
        outputStream.write(message.getBytes());
    }
}
