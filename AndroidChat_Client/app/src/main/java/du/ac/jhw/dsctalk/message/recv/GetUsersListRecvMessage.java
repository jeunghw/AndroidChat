package du.ac.jhw.dsctalk.message.recv;

import java.io.DataInputStream;
import java.io.IOException;

import du.ac.jhw.dsctalk.client.MessageAcceptor;
import du.ac.jhw.dsctalk.message.RecvMessage;

public class GetUsersListRecvMessage extends RecvMessage {

    private int userIDsSize;
    private String[] userIDs;

    public GetUsersListRecvMessage(DataInputStream input, int messageSize) throws IOException {
        super(getUsersList, messageSize);
        userIDsSize = input.readInt();
        userIDs = new String[userIDsSize];
        for(int i = 0; i < userIDsSize; i++){
            userIDs[i] = readString(input, userIDSize).trim();
        }
    }

    public String[]  getUserIDs(){
        return userIDs;
    }

    @Override
    public void execute(MessageAcceptor acceptor) {
        acceptor.accept(this);
    }
}
