package du.ac.jhw.dsctalk.UIFragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

import du.ac.jhw.dsctalk.ListView.ChatCount;
import du.ac.jhw.dsctalk.ListView.ChatCountAdapter;
import du.ac.jhw.dsctalk.ListView.ChatListItem;
import du.ac.jhw.dsctalk.MainActivity;
import du.ac.jhw.dsctalk.R;
import du.ac.jhw.dsctalk.client.TalkClientSender;

public class UserListFragment extends Fragment {

    private TalkClientSender talkClientSender = TalkClientSender.getInstance();
    private String[] iDList;

    Fragment fragment;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    Bundle args = new Bundle();

    Button disconnectButton;

    private static Hashtable<String, ArrayList> chatListItemHashtable= new Hashtable<>();

    public Hashtable getchatListItemHashtable() {
        return chatListItemHashtable;
    }

    private static Hashtable<String, Integer> chatCountHashtable = new Hashtable<>();

    public Hashtable<String, Integer> getChatCountHashtalbe() {
        return chatCountHashtable;
    }

    private static UserListFragment instance = new UserListFragment();

    public static UserListFragment getInstance() {
        return instance;
    }

    ArrayList<ChatCount> chatCounts;
    ListView iDListView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View userListUI = inflater.inflate(R.layout.userlist, container, false);

        disconnectButton = userListUI.findViewById(R.id.disconnect);
        iDList = getArguments().getStringArray("iDList");

        iDListView = userListUI.findViewById(R.id.ID_List);
        chatCounts = new ArrayList<>();
        int i=0;
        while(i < iDList.length) {
            ChatCount chatCount = new ChatCount();
            chatCount.setiD(iDList[i]);
            if(chatCountHashtable.containsKey(iDList[i]))
                chatCount.setCount(chatCountHashtable.get(iDList[i]));
            else
                chatCount.setCount(0);
            chatCounts.add(chatCount);
            i++;

            ChatCountAdapter chatCountAdapter = new ChatCountAdapter(chatCounts);
            iDListView.setAdapter(chatCountAdapter);
        }

//        ChatCountAdapter chatCountAdapter = new ChatCountAdapter(chatCounts);
//        iDListView.setAdapter(chatCountAdapter);

//        ArrayAdapter iDListadapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, iDList);
//
//        ListView iDListView = userListUI.findViewById(R.id.ID_List);
//        iDListView.setAdapter(iDListadapter);

        iDListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String OtherID = (String)parent.getItemAtPosition(position);
                String OtherID = iDList[position];
                //Toast.makeText(getActivity(),OtherID,Toast.LENGTH_SHORT).show();

                chatCountHashtable.put(OtherID,0);


                if(!chatListItemHashtable.containsKey(OtherID)) {
                    ArrayList<ChatListItem> chatListItems = new ArrayList<>();
                    chatListItemHashtable.put(OtherID, chatListItems);
                }

                fragment = new ChatFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Base_Fragment,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                args.putString("MyID",getArguments().getString("MyID"));
                args.putString("OtherID", OtherID);
                fragment.setArguments(args);

//                args.putString("destID",destID);
//
//                fragment = new ChatFragment();
//                fragment.setArguments(args);

//                fragmentTransaction  = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();

            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talkClientSender.close();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        return userListUI;
    }
}
