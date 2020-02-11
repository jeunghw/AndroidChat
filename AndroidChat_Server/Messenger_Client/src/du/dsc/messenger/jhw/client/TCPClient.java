package du.dsc.messenger.jhw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	
	private String host;
	private int port;

	public TCPClient(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void connectTest(){
		InetAddress serverAddr;
		try {
			serverAddr = InetAddress.getByName(host);
			Socket socket = new Socket(serverAddr, port);
			
			OutputStream out = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
			writer.print(writer);
			
			InputStream in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = reader.readLine();
			
			System.out.println("line : " + line);
			
			writer.flush();
			writer.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
