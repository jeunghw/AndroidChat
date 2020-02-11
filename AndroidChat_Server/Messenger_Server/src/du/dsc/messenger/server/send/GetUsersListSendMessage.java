package du.dsc.messenger.server.send;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;

import du.dsc.messenger.server.message.SendMessage;

public class GetUsersListSendMessage extends SendMessage {
	
	private int userIDsSize = 0;
	private String[] userIDs;

	public GetUsersListSendMessage(String[] userIDs) {
		super(GetUsersList, ClientIntSize + (userIDs.length * userIDSize));
		userIDsSize = userIDs.length;
		this.userIDs = userIDs;
	}

	@Override
	protected void SendBody(TCPServerConnection serverConnection) throws IndyIOException {
		writeInt(serverConnection, userIDsSize);
		for(int i = 0; i < userIDsSize; i++){
			writeString(serverConnection, userIDs[i] + "\0", userIDSize);
		}
	}

}
