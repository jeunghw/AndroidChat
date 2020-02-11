package du.dsc.messenger.server.message;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;
import org.indy.io.NotConnectedException;
import org.indy.io.PeerDisconnectedException;
import org.indy.io.ReadTimedOutException;

import du.dsc.messenger.server.MessageExecuteAcceptor;
import du.dsc.messenger.server.recv.ChatRecvMessage;
import du.dsc.messenger.server.recv.GetUsersListRecvMessage;
import du.dsc.messenger.server.recv.LoginRecvMessage;
import du.dsc.messenger.server.recv.UnkownedRecvMessage;

public abstract class RecvMessage extends BaseMessage {

	public RecvMessage(int MessageType, int MessageSize) {
		super(MessageType, MessageSize);
	}

	public abstract void Execute(MessageExecuteAcceptor Acceptor, TCPServerConnection serverConnection);
	
	protected static String ReadString(TCPServerConnection serverConnection, int Length) throws PeerDisconnectedException, ReadTimedOutException, NotConnectedException, IndyIOException {
		byte[] MessageByte = new byte[Length];
		serverConnection.readBuffer(MessageByte, Length);
		return new String(MessageByte).trim();
	}
	
	// 32비트의 안드로이드 모튤에서 64비트의 컴퓨터 서버에 전송할때 비트변경을 위해서 사용
	public static int readInt(TCPServerConnection serverConnection) throws PeerDisconnectedException, ReadTimedOutException, NotConnectedException, IndyIOException{
		byte[] tempBuffer = new byte[4];
		byte[] byteBuffer = new byte[4];
		short[] temp = new short[4];
		
		int readedSize = 0;
		while(readedSize < 4){
			int readSize = serverConnection.readBuffer(tempBuffer, 4 - readedSize);
			System.arraycopy(tempBuffer, 0, byteBuffer, readedSize, readSize);
			readedSize = readedSize + readSize;
		}
		
		for(int i = 0; i < 4; i++){
			temp[i] = (short)(byteBuffer[i] & 0xff);
		}
		
		return (temp[0] << 24) + (temp[1] << 16) + (temp[2] << 8) + (temp[3]);
	}
	
	public static RecvMessage recvMessage(TCPServerConnection serverConnection) throws PeerDisconnectedException, ReadTimedOutException, NotConnectedException, IndyIOException{
		int MessageType = readInt(serverConnection);
		int MessageSize = readInt(serverConnection);
		if(MessageType == LoginType) {
			return new LoginRecvMessage(serverConnection, MessageSize);
		}else if(MessageType == GetUsersList) {
			return new GetUsersListRecvMessage(serverConnection, MessageSize);
		}else if(MessageType == chat) {
			return new ChatRecvMessage(serverConnection, MessageSize);
		}else {
			return new UnkownedRecvMessage(serverConnection, MessageType, MessageSize);
		}
	}

}
