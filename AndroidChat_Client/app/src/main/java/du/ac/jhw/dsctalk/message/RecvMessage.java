package du.ac.jhw.dsctalk.message;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;

import du.ac.jhw.dsctalk.client.MessageAcceptor;
import du.ac.jhw.dsctalk.message.recv.ChatRecvMessage;
import du.ac.jhw.dsctalk.message.recv.GetUsersListRecvMessage;
import du.ac.jhw.dsctalk.message.recv.LoginRecvMessage;
import du.ac.jhw.dsctalk.message.recv.UnkownedMessage;

/**
 * Created by 1203-00 on 2018-04-06.
 */

public abstract class RecvMessage extends BaseMessage {
    public RecvMessage(int messageType, int messageSize) {
        super(messageType, messageSize);
    }

    public abstract void execute(MessageAcceptor acceptor);

    public static String readString(DataInputStream reader, int length) throws IOException {
        byte[] dataByte = new byte[length];
        reader.read(dataByte, 0, length);
        return new String(dataByte);
    }

    public static RecvMessage recvMessage(DataInputStream reader) throws IOException {
        int messageType = reader.readInt();
        int messageSize = reader.readInt();
        switch(messageType){
            case loginType:
                return new LoginRecvMessage(reader, messageSize);
            case getUsersList:
                return new GetUsersListRecvMessage(reader, messageSize);
            case chat:
                return new ChatRecvMessage(reader, messageSize);
            default:
                return new UnkownedMessage(messageType, messageSize);
        }
    }

}
