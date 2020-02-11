package du.dsc.messenger.server;

import java.io.PrintStream;

import org.indy.TCPServerConnection;

import du.dsc.messenger.server.recv.ChatRecvMessage;
import du.dsc.messenger.server.recv.GetUsersListRecvMessage;
import du.dsc.messenger.server.recv.LoginRecvMessage;
import du.dsc.messenger.server.recv.UnkownedRecvMessage;

public class PrintAcceptor implements MessageExecuteAcceptor {

	private PrintStream out;
	
	public PrintAcceptor(PrintStream out) {
		this.out = out;
	}
	
	@Override
	public void Accept(LoginRecvMessage Message, TCPServerConnection serverConnection) {
		out.println("LoginRecvMessage (userID : " + Message.getUserID() +")");

	}

	@Override
	public void Accept(GetUsersListRecvMessage Message, TCPServerConnection serverConnection) {
		out.println("GetUsersListRecvMessage");

	}

	@Override
	public void Accept(UnkownedRecvMessage Message, TCPServerConnection serverConnection) {

	}

	@Override
	public void Accept(ChatRecvMessage Message, TCPServerConnection serverConnection) {
		out.println("ChatRecvMessage");
		//out.println("UserID : " + Message.getUserID());
		//out.println("DestID : " + Message.getDestID());
		//out.println("Message : " + Message.getMessage());
		
	}

}
