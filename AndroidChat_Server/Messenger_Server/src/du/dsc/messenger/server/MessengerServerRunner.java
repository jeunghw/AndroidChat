package du.dsc.messenger.server;

import org.indy.PeerThread;
import org.indy.TCPServer;
import org.indy.TCPServerConnection;
import org.indy.TCPServerExecuteListener;
import org.indy.io.IndyIOException;

public class MessengerServerRunner {
	
	DSTCPServerExecuteListener executeListener = null;
	
	private TCPServer tcpServer = new TCPServer(){
		
		@Override
		protected void doDisconnect(PeerThread thread){
			super.doDisconnect(thread);
			executeListener.onDisconnect(thread.getConnection());
		}
	};
	
//	private TCPServer tcpServer = new TCPServer();
	
	
	public MessengerServerRunner(int serverPort){
		tcpServer.setDefaultPort(serverPort);
	}
	
	public void serverRunning(DSTCPServerExecuteListener serverExecuteListener) throws IndyIOException{
//	public void serverRunning(TCPServerExecuteListener serverExecuteListener) throws IndyIOException{
		executeListener = serverExecuteListener;
		tcpServer.addServerExecuteListener(serverExecuteListener);
		tcpServer.setActive(true);
	}
	
	public interface DSTCPServerExecuteListener extends TCPServerExecuteListener {
		public abstract void onDisconnect(TCPServerConnection serverConnection);
	}
}
