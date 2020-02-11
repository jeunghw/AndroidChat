package du.dsc.messenger.server.recv;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;
import org.indy.io.NotConnectedException;
import org.indy.io.PeerDisconnectedException;
import org.indy.io.ReadTimedOutException;

import du.dsc.messenger.server.MessageExecuteAcceptor;
import du.dsc.messenger.server.message.RecvMessage;

public class ChatRecvMessage extends RecvMessage {

	private String userID;
	private String destID;
	private String message;

	public ChatRecvMessage(TCPServerConnection serverConnection, int messageSize) throws PeerDisconnectedException, ReadTimedOutException, NotConnectedException, IndyIOException {
		super(chat, messageSize);
		userID = ReadString(serverConnection, userIDSize);
		destID = ReadString(serverConnection, userIDSize);
		
		//userID = ReadString(serverConnection, 5);
		//destID = ReadString(serverConnection, 5);
		
		message = ReadString(serverConnection, messageSize - (userIDSize * 2));
		System.out.println("ChatRecvMessage UserID : " + userID);
		System.out.println("ChatRecvMessage DestID : " + destID);
		System.out.println("ChatRecvMessage Message : " + message);
	}
	
	public String getUserID(){
		return userID;
	}
	
	public String getDestID(){
		return destID;
	}
	
	public String getMessage(){
		return message;
	}

	@Override
	public void Execute(MessageExecuteAcceptor Acceptor, TCPServerConnection serverConnection) {
		Acceptor.Accept(this, serverConnection);
	}

}
