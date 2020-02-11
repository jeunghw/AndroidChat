package du.dsc.messenger.server.sending;

import java.util.Hashtable;

import org.indy.TCPServerConnection;

import du.dsc.messenger.server.message.SendMessage;

public class SendingThreadManager {
	
	private static SendingThreadManager instance = new SendingThreadManager();
	private Hashtable<TCPServerConnection, SendingThread> connectionsHashtable =
			new Hashtable<TCPServerConnection, SendingThread> ();
	
	public static SendingThreadManager getInstance(){
		return instance;
	}

	private SendingThreadManager(){
	}
	
	private SendingThread getSendingThread(TCPServerConnection serverConnection){
		SendingThread sendingThread = connectionsHashtable.get(serverConnection);
		if(sendingThread == null){
			sendingThread = new SendingThread(serverConnection);
			connectionsHashtable.put(serverConnection, sendingThread);
			sendingThread.start();
			return sendingThread;
		}else{
			return sendingThread;
		}
	}
	
	public void SendingMessage(TCPServerConnection serverConnection, SendMessage sendMessage){
		SendingThread sendingThread = getSendingThread(serverConnection);
		sendingThread.PutMessage(sendMessage);
		//끝나도 MessagerExecuteListener로 돌아가지 않음
	}
	
	public void removeSendingThread(TCPServerConnection serverConnection){
		SendingThread sendingThread = connectionsHashtable.get(serverConnection);
		sendingThread.finish();
		connectionsHashtable.remove(serverConnection);
	}
	
	public int getSize() {
		return connectionsHashtable.size();
	}
}
