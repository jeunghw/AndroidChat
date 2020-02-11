package du.ac.jhw.dsctalk.message.recv;

import java.io.DataInputStream;
import java.io.IOException;

import du.ac.jhw.dsctalk.client.MessageAcceptor;
import du.ac.jhw.dsctalk.message.RecvMessage;

/**
 * Created by 1203-00 on 2018-04-27.
 */

public class LoginRecvMessage extends RecvMessage {

    public static final int loginSucess = 10;
    public static final int loginFailed = 20;

    private int loginResult;

    public LoginRecvMessage(DataInputStream input,int messageSize) throws IOException {
        super(loginType, messageSize);
        loginResult = input.readInt();
    }

    public int getLoginResult(){
        return loginResult;
    }

    @Override
    public void execute(MessageAcceptor acceptor) {
        acceptor.accept(this);
    }
}
