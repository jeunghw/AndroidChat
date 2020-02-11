package du.ac.jhw.dsctalk.UIFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;

import du.ac.jhw.dsctalk.ListView.ChatListAdapter;
import du.ac.jhw.dsctalk.ListView.ChatListItem;
import du.ac.jhw.dsctalk.R;
import du.ac.jhw.dsctalk.client.TalkClientSender;

public class ChatFragment extends Fragment {

    //send에 사용
    String MyID;
    String OtherID;

    private TalkClientSender talkClientSender = TalkClientSender.getInstance();
    private UserListFragment userListFragment = UserListFragment.getInstance();

    private EditText chatText;
    private Button sendChatButton;

    private ListView listView;
    ChatListAdapter chatListAdapter;

    private Hashtable<String, ArrayList> chatListItemHashtable = userListFragment.getchatListItemHashtable();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View chatUI = inflater.inflate(R.layout.chat, container, false);

        MyID = getArguments().getString("MyID");
        OtherID = getArguments().getString("OtherID");

        chatText = chatUI.findViewById(R.id.chat_Text);
        sendChatButton = chatUI.findViewById(R.id.send_chat_button);

        listView = chatUI.findViewById(R.id.chatting);
        if(chatListItemHashtable.get(OtherID) != null) {
            chatListAdapter = new ChatListAdapter(chatListItemHashtable.get(OtherID));
            listView.setAdapter(chatListAdapter);
        }


        sendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatMessage = chatText.getText().toString();
                if(chatMessage.length() <= 0) {

                }
                else {
                    chatText.setText(null);
                    talkClientSender.sendChatMessage(MyID, OtherID, chatMessage);

                    ChatListItem chatListItem = new ChatListItem();
                    chatListItem.SetID(MyID);
                    chatListItem.SetMessage(chatMessage);
                    chatListItemHashtable.get(OtherID).add(chatListItem);

                    chatListAdapter = new ChatListAdapter(chatListItemHashtable.get(OtherID));
                    listView.setAdapter(chatListAdapter);
                }

            }
        });


        return chatUI;
    }

}
