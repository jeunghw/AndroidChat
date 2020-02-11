package du.dsc.messenger.server.recv;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;
import org.indy.io.NotConnectedException;
import org.indy.io.PeerDisconnectedException;
import org.indy.io.ReadTimedOutException;

import du.dsc.messenger.server.MessageExecuteAcceptor;
import du.dsc.messenger.server.message.RecvMessage;

public class LoginRecvMessage extends RecvMessage {

	private String UserID;
	
	public LoginRecvMessage(TCPServerConnection serverConnection, int MessageSize) throws PeerDisconnectedException, ReadTimedOutException, NotConnectedException, IndyIOException {
		super(LoginType, MessageSize);
		UserID = ReadString(serverConnection, MessageSize);
		
	}
	
	/*@Override
	public String toString() {
		return "LoginRecvMessage (UserID : " + UserID + ")";
	}*/
	
	public String getUserID() {
		return UserID;
	}

	@Override
	public void Execute(MessageExecuteAcceptor Acceptor, TCPServerConnection serverConnection) {
		Acceptor.Accept(this, serverConnection);
		
	}

}
