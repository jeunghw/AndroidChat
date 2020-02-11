package du.dsc.messenger.server.send;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;

import du.dsc.messenger.server.message.SendMessage;

public class LoginSendMessage extends SendMessage {

	public static final int loginSucess = 10;
	public static final int LoginFailed = 20;
	
	private int loginResult =0;
	
	public LoginSendMessage(int loginResult) {
		super(LoginType, ClientIntSize);
		this.loginResult = loginResult;
	}

	@Override
	protected void SendBody(TCPServerConnection serverConnection) throws IndyIOException {
		writeInt(serverConnection, loginResult);
	}

}
