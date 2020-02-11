package du.ac.jhw.dsctalk.client;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import du.ac.jhw.dsctalk.ListView.ChatListItem;
import du.ac.jhw.dsctalk.message.RecvMessage;
import du.ac.jhw.dsctalk.message.recv.ChatRecvMessage;
import du.ac.jhw.dsctalk.message.recv.GetUsersListRecvMessage;
import du.ac.jhw.dsctalk.message.recv.LoginRecvMessage;

/**
 * Created by 1203-00 on 2018-03-23.
 */

public class TalkClientRecver extends Thread implements MessageAcceptor{

    private static TalkClientRecver instance = new TalkClientRecver();

    private Socket clientSocket;
    private DataInputStream reader;

    private boolean isRunning = true;

    private Handler recvMessageHandler;

    private boolean isRunning_Run = false;

    //private  ArrayList<ChatListItem> chatListItems = new ArrayList<>();

    private TalkClientRecver(){
    }

    public static TalkClientRecver getInstance(){
        return instance;
    }

    public void setRecvMessageHandler(Handler recvMessageHandler){
        this.recvMessageHandler = recvMessageHandler;
    }
    public void setClientSocket(Socket clientSocket){
        this.clientSocket = clientSocket;
        try{
            this.reader = new DataInputStream(clientSocket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(isRunning){
            try{
//                Log.d("dsTalk", "before readLine");
//                String line = reader.readLine();
//                Log.d("dsTalk", "after readLine");
//                Log.d("dsTalk", "recv line : " + line);
                RecvMessage recvMessage = RecvMessage.recvMessage(reader);
                recvMessage.execute(this);
                Message handlerMessage = recvMessageHandler.obtainMessage(recvMessage.getMessageType());
                handlerMessage.obj = recvMessage;
                recvMessageHandler.sendMessage(handlerMessage);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void accept(LoginRecvMessage message) {
        Log.d("dsTalk", "LoginRecvMessage (result : " + message.getLoginResult() + ")");
    }

    @Override
    public void accept(GetUsersListRecvMessage message) {
        Log.d("dsTalk", "GetUsersListRecvMessage");
        String[] userIDs = message.getUserIDs();
        for(int i = 0; i < userIDs.length; i++){
            Log.d("dsTalk", "userID : " + userIDs[i]);
        }
    }

    @Override
    public void accept(ChatRecvMessage mesage) {
        Log.d("deTalk", "ChatRecvMessage");
        String userID = mesage.getUserID();
        String destID = mesage.getDestID();
        String message = mesage.getMessage();
        Log.d("dsTalk", "userID : " + userID + " destID : " + destID + " message : " + message);
    }

//    public ArrayList getChatArryList() {
//        return chatListItems;
//    }
}