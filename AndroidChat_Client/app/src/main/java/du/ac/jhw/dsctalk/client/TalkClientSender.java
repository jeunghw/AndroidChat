package du.ac.jhw.dsctalk.client;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Queue;

import du.ac.jhw.dsctalk.message.SendMessage;
import du.ac.jhw.dsctalk.message.send.ChatSendMessage;
import du.ac.jhw.dsctalk.message.send.GetUsersListSendMessage;
import du.ac.jhw.dsctalk.message.send.LoginSendMessage;
import du.ac.jhw.dsctalk.message.send.UnkowneMessage;

/**
 * Created by 1203-00 on 2018-03-16.
 */

public class TalkClientSender extends Thread{

    private static TalkClientSender instance = new TalkClientSender();

    private String host = "";
    private int port = -1;

    private Socket clientSocket;
//    private PrintWriter writer;

    private DataOutputStream outputStream;

    private Queue<SendMessage> sendMessageQueue = new ArrayDeque<SendMessage>();

    private boolean isRunning = true;
    private boolean isRunning_Run = false;

    private TalkClientSender() {
    }

    public static TalkClientSender getInstance(){
        return instance;
    }

    @Override
    public void run(){
        try {
            tcpConnect();
//            int messageType = 1001;
//            String userID = "abcd";
//            int messageSize = userID.getBytes().length;
//            outputStream.writeInt(messageType);
//            outputStream.writeInt(messageSize);
//            outputStream.write(userID.getBytes());
            int count = 0;
            while(isRunning){
                SendMessage sendMessage = getSendMessage();
                if(sendMessage != null){
                    sendMessage.sending(outputStream);
                }else{
                    if(count > 2000) {
                        putSendMessage(new UnkowneMessage(" "));
                        count=0;
                    } else {
                        try {
                            Thread.sleep(50);
                            count++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tcpClose();
    }

    public void sendLoginMessage(String userID){
        LoginSendMessage message = new LoginSendMessage(userID);
        putSendMessage(message);
    }

    public void sendGetUserList(){
        GetUsersListSendMessage message = new GetUsersListSendMessage();
        putSendMessage(message);
    }

    public void sendChatMessage(String userID, String destID, String chatmessage){
        ChatSendMessage message = new ChatSendMessage(userID, destID, chatmessage);
        putSendMessage(message);
    }

    private void putSendMessage(SendMessage message){
        synchronized (sendMessageQueue){
            sendMessageQueue.offer(message);
        }
    }

    private SendMessage getSendMessage(){
       SendMessage message = null;
        synchronized (sendMessageQueue){
            message = sendMessageQueue.poll();
        }
        return message;
    }

    public void setHostPort(String host, int port){
        this.host = host;
        this.port = port;
    }

    public Socket getClientSocket(){
        return clientSocket;
    }

    public void close(){
        this.isRunning = false;
    }

    public Boolean getisRunning(){
         return this.isRunning;
    }

    public boolean getisRunning_Run() {
       return  this.isRunning_Run;
    }

    public void setisRunning_Run(boolean x) {
        this.isRunning_Run = x;
    }

    private void tcpConnect() throws UnknownHostException, IOException {
        Log.d("dsTalk", "call connect");
        InetAddress hostAddress = InetAddress.getByName(host);

        clientSocket = new Socket(hostAddress, port);
        clientSocket.setKeepAlive(true);
        clientSocket.setSoTimeout(0);
//        writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    private void tcpClose() {
        try {
            outputStream.flush();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
