package du.ac.jhw.dsctalk.message.send;

import java.io.DataOutputStream;
import java.io.IOException;

import du.ac.jhw.dsctalk.message.SendMessage;

/**
 * Created by 1203-00 on 2018-04-06.
 */

public class LoginSendMessage extends SendMessage {

    private String userID;

    public LoginSendMessage(String userID) {
        super(loginType, userID.getBytes().length);
        this.userID = userID;
    }

    @Override
    public void sending(DataOutputStream outputStream) throws IOException {
        sendingHeader(outputStream);
        outputStream.write(userID.getBytes());
    }
}
