package du.dsc.messenger.server.sending;

import java.util.ArrayDeque;
import java.util.Queue;

import org.indy.TCPServerConnection;
import org.indy.io.IndyIOException;

import du.dsc.messenger.server.message.SendMessage;

public class SendingThread extends Thread {

	private TCPServerConnection serverConnection;
	private Queue<SendMessage> SendMessageQueue = new ArrayDeque<SendMessage> ();
	private boolean IsRunning = true;
	
	SendingThread(TCPServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}
	
	void PutMessage(SendMessage Message) {
		synchronized(SendMessageQueue) {
			SendMessageQueue.offer(Message);
		}
	}
	
	private SendMessage GetMessage() {
		SendMessage Message;
		synchronized(SendMessageQueue) {
			Message = SendMessageQueue.poll();
		}
		return Message;
	}
	
	@Override
	public void run() {
		while(IsRunning) {
			try {
				SendMessage Message = GetMessage();
				if(Message != null) {
					Message.sendMessage(serverConnection);
				}else {
					try {
						Thread.sleep(50);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}catch(IndyIOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void finish() {
		IsRunning = false;
	}
}
