package du.dsc.messenger.server;

import org.indy.TCPServerConnection;

import du.dsc.messenger.server.recv.ChatRecvMessage;
import du.dsc.messenger.server.recv.GetUsersListRecvMessage;
import du.dsc.messenger.server.recv.LoginRecvMessage;
import du.dsc.messenger.server.recv.UnkownedRecvMessage;

public interface MessageExecuteAcceptor {
	
	public void Accept(LoginRecvMessage Message, TCPServerConnection serverConnection);
	public void Accept(GetUsersListRecvMessage Message, TCPServerConnection serverConnection);
	public void Accept(ChatRecvMessage Message, TCPServerConnection serverConnection);
	public void Accept(UnkownedRecvMessage Message, TCPServerConnection serverConnection);
}
