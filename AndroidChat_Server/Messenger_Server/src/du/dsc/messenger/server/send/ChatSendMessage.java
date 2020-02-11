package du.dsc.messenger.server.send;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;

import du.dsc.messenger.server.message.SendMessage;
import du.dsc.messenger.server.recv.ChatRecvMessage;

public class ChatSendMessage extends SendMessage {

	private String userID;
	private String destID;
	private String message;
	
	public ChatSendMessage(ChatRecvMessage recvMessage){
		super(chat, (userIDSize * 2) + recvMessage.getMessage().getBytes().length);
		userID = recvMessage.getUserID();
		destID = recvMessage.getDestID();
		message = recvMessage.getMessage();
		
	}
	
	public ChatSendMessage(String userID, String destID, String message){
		super(chat, (userIDSize * 2) + message.getBytes().length);
		this.userID = userID;
		this.destID = destID;
		this.message = message;
	}

	@Override
	protected void SendBody(TCPServerConnection serverConnection) throws IndyIOException {
		writeString(serverConnection, userID, userIDSize);
		writeString(serverConnection, destID, userIDSize);
		writeString(serverConnection, message, message.getBytes().length);
	}

}
