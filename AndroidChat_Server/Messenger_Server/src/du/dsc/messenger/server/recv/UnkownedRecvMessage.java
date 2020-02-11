package du.dsc.messenger.server.recv;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;
import org.indy.io.NotConnectedException;
import org.indy.io.PeerDisconnectedException;
import org.indy.io.ReadTimedOutException;

import du.dsc.messenger.server.MessageExecuteAcceptor;
import du.dsc.messenger.server.message.RecvMessage;

public class UnkownedRecvMessage extends RecvMessage {

	public UnkownedRecvMessage(TCPServerConnection serverConnection, int MessageType, int MessageSize) throws PeerDisconnectedException, ReadTimedOutException, NotConnectedException, IndyIOException {
		super(MessageType, MessageSize);
		ReadString(serverConnection, MessageSize);
		
	}

	@Override
	public void Execute(MessageExecuteAcceptor Acceptor, TCPServerConnection serverConnection) {
		Acceptor.Accept(this, serverConnection);
		
	}

}
