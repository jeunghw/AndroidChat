package du.ac.jhw.dsctalk;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import du.ac.jhw.dsctalk.ListView.ChatCount;
import du.ac.jhw.dsctalk.ListView.ChatCountAdapter;
import du.ac.jhw.dsctalk.ListView.ChatListAdapter;
import du.ac.jhw.dsctalk.ListView.ChatListItem;
import du.ac.jhw.dsctalk.UIFragment.LoginFragment;
import du.ac.jhw.dsctalk.UIFragment.UserListFragment;
import du.ac.jhw.dsctalk.client.MessageAcceptor;
import du.ac.jhw.dsctalk.client.TalkClientRecver;
import du.ac.jhw.dsctalk.client.TalkClientSender;
import du.ac.jhw.dsctalk.message.RecvMessage;
import du.ac.jhw.dsctalk.message.recv.ChatRecvMessage;
import du.ac.jhw.dsctalk.message.recv.GetUsersListRecvMessage;
import du.ac.jhw.dsctalk.message.recv.LoginRecvMessage;

public class MainActivity extends AppCompatActivity implements MessageAcceptor, LoginFragment.GetUserID {

//    private EditText userIDText;
//    private Button sendUserIDButton;
//    private Button getUsersListButton;
//    private Button disconnectButton;

    private final String host = "192.168.0.163";
    private final int port = 1234;

    private TalkClientSender talkClientSender = TalkClientSender.getInstance();
    private TalkClientRecver talkClientRecver = TalkClientRecver.getInstance();
    private UserListFragment userListFragment = UserListFragment.getInstance();

    ListView listView;
    ChatListAdapter chatListAdapter;

    private Hashtable<String, ArrayList> chatListItemHashtable = userListFragment.getchatListItemHashtable();
    private Hashtable<String, Integer> chatCountHashtalbe = userListFragment.getChatCountHashtalbe();

    private final Handler recvMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
//            Toast.makeText(MainActivity.this, "message type : " + message.what, Toast.LENGTH_SHORT).show();
            RecvMessage recvMessage = (RecvMessage) message.obj;
            recvMessage.execute(MainActivity.this);
        }
    };

    Fragment fragment = null;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    Bundle args = new Bundle();
    String myUserID;

    ArrayList<ChatCount> chatCounts;
    ListView iDListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        talkClientSender.setHostPort(host, port);
        if(talkClientSender.getisRunning_Run() == false) {
            talkClientSender.start();
            talkClientSender.setisRunning_Run(talkClientSender.getisRunning());
        }
        Socket clientSocket = null;
        do{
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientSocket = talkClientSender.getClientSocket();
        }while(clientSocket == null);
        talkClientRecver.setRecvMessageHandler(recvMessageHandler);
        talkClientRecver.setClientSocket(clientSocket);
        if(talkClientRecver.getisRunning_Run() == false) {
            talkClientRecver.start();
            talkClientRecver.setisRunning_Run(talkClientRecver.getisRunning());
        }
//        userIDText = (EditText)findViewById(R.id.user_id_text);
//        sendUserIDButton = (Button)findViewById(R.id.send_user_id_button);
//        getUsersListButton = (Button)findViewById(R.id.send_get_users_list_button);
//        disconnectButton = (Button)findViewById(R.id.disconnect_server_button);

//        sendUserIDButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userID = userIDText.getText().toString();
//                LoginSendMessage message = new LoginSendMessage(userID);
//                talkClientSender.putSendMessage(message);
//            }
//        });
//
//        getUsersListButton.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                GetUsersListSendMessage message = new GetUsersListSendMessage();
//                talkClientSender.putSendMessage(message);
//            }
//        });
//
//        disconnectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                talkClientSender.close();
//            }
//        });

            fragment = new LoginFragment();

            fragmentTransaction  = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Base_Fragment, fragment);
            fragmentTransaction.commit();

    }

    @Override
    public void accept(LoginRecvMessage message) {
        Toast.makeText(MainActivity.this, "message type : " + message.getLoginResult(), Toast.LENGTH_SHORT).show();

        if(message.getLoginResult() == LoginRecvMessage.loginSucess){
            //talkClientSender.sendGetUserList();

        }else if(message.getLoginResult() == LoginRecvMessage.loginFailed){
            Toast.makeText(MainActivity.this, "이미 사용중인 아이디입니다.",Toast.LENGTH_SHORT).show();
            return;
        }else{

        }
    }

    @Override
    public void accept(GetUsersListRecvMessage message) {
        args.putString("MyID", myUserID);
        args.putStringArray("iDList", message.getUserIDs());
        fragment = new UserListFragment();
        fragment.setArguments(args);

        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Base_Fragment, fragment);
        //fragmentTransaction.addToBackStack(null);


        String[] iDList = message.getUserIDs();
        iDListView = findViewById(R.id.ID_List);
        chatCounts = new ArrayList<>();

        int i=0;
        while(i < iDList.length) {
            ChatCount chatCount = new ChatCount();
            chatCount.setiD(iDList[i]);
            if(chatCountHashtalbe.containsKey(iDList[i]))
                chatCount.setCount(chatCountHashtalbe.get(iDList[i]));
            else
                chatCount.setCount(0);
            chatCounts.add(chatCount);
            i++;

            ChatCountAdapter chatCountAdapter = new ChatCountAdapter(chatCounts);
            if(iDListView != null)
                iDListView.setAdapter(chatCountAdapter);
        }

        fragmentTransaction.commit();
    }



    @Override
    public void accept(ChatRecvMessage message) {
        ChatListItem chatListItem = new ChatListItem();
        chatListItem.SetID(message.getUserID());
        chatListItem.SetMessage(message.getMessage());

        if(!chatListItemHashtable.containsKey(message.getUserID())) {
            ArrayList<ChatListItem> chatListItems = new ArrayList<>();
            chatListItemHashtable.put(message.getUserID(), chatListItems);
        }

        int Count;
        if(!chatCountHashtalbe.containsKey(message.getUserID())) {
            Count = 1;
            chatCountHashtalbe.put(message.getUserID(), Count);
        } else {
            chatCountHashtalbe.put(message.getUserID(), chatCountHashtalbe.get(message.getUserID())+1);
        }

        chatListItemHashtable.get(message.getUserID()).add(chatListItem);
        Toast.makeText(this,message.getUserID() + " : " + chatCountHashtalbe.get(message.getUserID()) ,Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.chatting) ;
        chatListAdapter = new ChatListAdapter(chatListItemHashtable.get(message.getUserID()));
        if(listView != null) {
            listView.setAdapter(chatListAdapter);
        }
    }

    @Override
    public void GetMYUserID(String myUserID) {
        this.myUserID = myUserID;
    }

}


