package du.ac.jhw.dsctalk.message;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by 1203-00 on 2018-04-06.
 */

public abstract class SendMessage extends BaseMessage {

    public SendMessage(int messageType, int messageSize){
        super(messageType, messageSize);
    }

    protected void sendingHeader(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(messageType);
        outputStream.writeInt(messageSize);
    }

    public abstract void sending(DataOutputStream outputStream) throws IOException;
}
