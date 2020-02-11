package du.ac.jhw.dsctalk.client;

import du.ac.jhw.dsctalk.message.recv.ChatRecvMessage;
import du.ac.jhw.dsctalk.message.recv.GetUsersListRecvMessage;
import du.ac.jhw.dsctalk.message.recv.LoginRecvMessage;

public interface MessageAcceptor {
    public void accept(LoginRecvMessage message);
    public void accept(GetUsersListRecvMessage message);
    public void accept(ChatRecvMessage message);
}
