package du.dsc.messenger.server.recv;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;
import org.indy.io.NotConnectedException;
import org.indy.io.PeerDisconnectedException;
import org.indy.io.ReadTimedOutException;

import du.dsc.messenger.server.MessageExecuteAcceptor;
import du.dsc.messenger.server.message.RecvMessage;

public class GetUsersListRecvMessage extends RecvMessage {
	
	public GetUsersListRecvMessage(TCPServerConnection serverConnection, int MessageSize) {
		super(GetUsersList, MessageSize);
	}

	/*@Override
	public String toString() {
		return "GetUsersListRecvMessage";
	}*/

	@Override
	public void Execute(MessageExecuteAcceptor Acceptor, TCPServerConnection serverConnection) {
		Acceptor.Accept(this, serverConnection);
		
	}
	
	

}
