package du.ac.jhw.dsctalk.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import du.ac.jhw.dsctalk.R;

public class ChatListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    private ArrayList<ChatListItem> listData;
    private int listCot;

    public ChatListAdapter(ArrayList<ChatListItem> listData) {
        this.listData = listData;
        listCot = this.listData.size();
    }

    @Override
    public int getCount() {
        return listCot;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            final Context context = parent.getContext();
            if(layoutInflater == null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = layoutInflater.inflate(R.layout.chating_listview, null);
        }

        TextView iDText = convertView.findViewById(R.id.IDText);
        TextView messageText = convertView.findViewById(R.id.MessageText);

        iDText.setText(listData.get(position).GetID());
        messageText.setText(listData.get(position).GetMessage());

        return convertView;
    }
}
