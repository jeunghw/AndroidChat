package du.dsc.messenger.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.indy.IndyException;
import org.indy.TCPServer;
import org.indy.TCPServerConnection;
import org.indy.TCPServerExecuteListener;

import du.dsc.messenger.server.message.RecvMessage;
import du.dsc.messenger.server.message.SendMessage;
import du.dsc.messenger.server.recv.ChatRecvMessage;
import du.dsc.messenger.server.recv.GetUsersListRecvMessage;
import du.dsc.messenger.server.recv.LoginRecvMessage;
import du.dsc.messenger.server.recv.UnkownedRecvMessage;
import du.dsc.messenger.server.send.ChatSendMessage;
import du.dsc.messenger.server.send.GetUsersListSendMessage;
import du.dsc.messenger.server.send.LoginSendMessage;
import du.dsc.messenger.server.sending.SendingThreadManager;

public class MessagerExecuteListener implements MessengerServerRunner.DSTCPServerExecuteListener, MessageExecuteAcceptor {
	
	private MessageExecuteAcceptor printAcceptor = new PrintAcceptor(System.out);
	
	private Hashtable<String, TCPServerConnection> connectionsByUserIDTable = new Hashtable<String, TCPServerConnection> ();
	
	private Hashtable<TCPServerConnection, String> connectionsByConnectTable = new Hashtable<TCPServerConnection, String> ();
	
	private List<String> userIDsList = new ArrayList<String>();
	
	private static MessagerExecuteListener instance = new MessagerExecuteListener();
	
	private MessagerExecuteListener(){
		
	}
	public static MessagerExecuteListener getInstance() {		//½Ì±ÛÅæ ÆÐÅÏ »ç¿ë
		return instance;
	}
	
	@Override
	public void onExecute(TCPServer tcpServer, TCPServerConnection serverConnection) throws IndyException {
		System.out.println("call onExecute");
		RecvMessage recvMessage = RecvMessage.recvMessage(serverConnection);
		recvMessage.Execute(printAcceptor, serverConnection);
		recvMessage.Execute(this, serverConnection);
		
	}

	@Override
	public void Accept(LoginRecvMessage Message, TCPServerConnection serverConnection) {	//¾ï¼ÁÆ®ÆäÅÏ»ç¿ë
		
		LoginSendMessage sendMessage;
		if(connectionsByUserIDTable.containsKey(Message.getUserID())){
			sendMessage = new LoginSendMessage(LoginSendMessage.LoginFailed);
		}else{
			connectionsByUserIDTable.put(Message.getUserID(), serverConnection);
			connectionsByConnectTable.put(serverConnection, Message.getUserID());
			userIDsList.add(Message.getUserID());
			sendMessage = new LoginSendMessage(LoginSendMessage.loginSucess);
		}
		SendingThreadManager sendingThreadManager = SendingThreadManager.getInstance();
		sendingThreadManager.SendingMessage(serverConnection, sendMessage);
		sendAllUserList();
	}

	@Override
	public void Accept(GetUsersListRecvMessage Message, TCPServerConnection serverConnection) {
		String[] userIDsArray = userIDsList.toArray(new String[userIDsList.size()]);
//		String[] userIDsArray = new String[userIDsList.size()];
//		for(int i = 0; i < userIDsList.size(); i++){
//			userIDsArray[i] = userIDsList.get(i);
//		}
		GetUsersListSendMessage sendMessage = new GetUsersListSendMessage(userIDsArray);
		SendingThreadManager sendingThreadManager = SendingThreadManager.getInstance();
		sendingThreadManager.SendingMessage(serverConnection, sendMessage);
		
	}
	
	@Override
	public void Accept(ChatRecvMessage message, TCPServerConnection serverConnection) {
		ChatSendMessage sendMessage = new ChatSendMessage(message);
		sendMessage(message.getDestID(), sendMessage);
		System.out.println("ChatSendMessage_Accept");
	}

	@Override
	public void Accept(UnkownedRecvMessage Message, TCPServerConnection serverConnection) {
		//System.out.println("MessagerExecuteListener Accept UnkownedMessage");
		
	}
	
	@Override
	public void onDisconnect(TCPServerConnection serverConnection) {
		SendingThreadManager sendingThreadManager = SendingThreadManager.getInstance();
		sendingThreadManager.removeSendingThread(serverConnection);
		String userID = connectionsByConnectTable.get(serverConnection);
		connectionsByUserIDTable.remove(userID);
		connectionsByConnectTable.remove(serverConnection);
		userIDsList.remove(userID);
		sendAllUserList();
	}
	
	private void sendAllUserList(){
		Iterator<TCPServerConnection> serverConnectionIterator = connectionsByUserIDTable.values().iterator();
		String[] userIDsArray = userIDsList.toArray(new String[userIDsList.size()]);
		GetUsersListSendMessage sendMessage = new GetUsersListSendMessage(userIDsArray);
		while(serverConnectionIterator.hasNext()){
			TCPServerConnection serverConnection = serverConnectionIterator.next();
			SendingThreadManager sendingThreadManager = SendingThreadManager.getInstance();
			sendingThreadManager.SendingMessage(serverConnection, sendMessage);
		}
	}
	
	private void sendMessage(String userID, SendMessage message){
		TCPServerConnection serverConnection = connectionsByUserIDTable.get(userID);
		System.out.println(userID);
		System.out.println(serverConnection);
		SendingThreadManager sendingThreadManager = SendingThreadManager.getInstance();
		sendingThreadManager.SendingMessage(serverConnection, message);
	}
}
