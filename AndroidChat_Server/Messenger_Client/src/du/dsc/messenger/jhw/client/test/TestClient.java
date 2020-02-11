package du.dsc.messenger.jhw.client.test;

import du.dsc.messenger.jhw.client.TCPClient;

public class TestClient {

	public static void main(String[] args) {
		TCPClient tcpClient = new TCPClient("127.0.0.1", 1234);

		tcpClient.connectTest();
	}

}