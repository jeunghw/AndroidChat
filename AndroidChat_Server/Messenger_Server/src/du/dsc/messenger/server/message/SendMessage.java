package du.dsc.messenger.server.message;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;
import org.indy.io.NotConnectedException;

public abstract class SendMessage extends BaseMessage {

	public SendMessage(int MessageType, int MessageSize) {
		super(MessageType, MessageSize);
	}

	protected void SendHeader(TCPServerConnection serverConnection) throws IndyIOException{
		writeInt(serverConnection, MessageType);
		writeInt(serverConnection, MessageSize);
	}
	
	public static void writeInt(TCPServerConnection serverConnection, int value) throws IndyIOException{
		byte[] intByte = new byte[4];
		intByte[0] = (byte)(value >> 24);
		intByte[1] = (byte)(value >> 16);
		intByte[2] = (byte)(value >> 8);
		intByte[3] = (byte)value;
		serverConnection.writeBuffer(intByte);
	}
	
	public static void writeString(TCPServerConnection serverConnection, String value, int length) throws NotConnectedException, IndyIOException{
		byte[] stringByte = new byte[length];
		if(value.getBytes().length < length){
			System.arraycopy(value.getBytes(), 0, stringByte, 0, value.getBytes().length);
			for(int i = value.getBytes().length; i < length; i++){
				stringByte[i] = 0;
			}
		}else{
			System.arraycopy(value.getBytes(), 0, stringByte, 0, length);
		}
		serverConnection.writeBuffer(stringByte);
	}
	
	public void sendMessage(TCPServerConnection serverConnection) throws IndyIOException {
		SendHeader(serverConnection);
		SendBody(serverConnection);
	}
	
	protected abstract void SendBody(TCPServerConnection serverConnection) throws IndyIOException;
}
