package du.dsc.messenger.server.test;

import org.indy.io.IndyIOException;

import du.dsc.messenger.server.MessengerServerRunner;
import du.dsc.messenger.server.MessagerExecuteListener;

public class TestServer {

	public static void main(String[] args) {
		
		MessengerServerRunner serverRunner = new MessengerServerRunner(1234);
		MessagerExecuteListener executeListener = MessagerExecuteListener.getInstance();
		
		try {
			serverRunner.serverRunning(executeListener);
		} catch (IndyIOException e) {
			e.printStackTrace();
		}
	}

}
