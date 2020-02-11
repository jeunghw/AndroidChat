package du.ac.jhw.dsctalk.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import du.ac.jhw.dsctalk.R;

public class ChatCountAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    private ArrayList<ChatCount> listData = new ArrayList<>();
    private int listCot;

    public ChatCountAdapter(ArrayList<ChatCount> arrayList) {
        this.listData = arrayList;
        listCot = listData.size();
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
            convertView = layoutInflater.inflate(R.layout.chatcount, null);
        }

        TextView iDText = convertView.findViewById(R.id.chatid);
        TextView countText = convertView.findViewById(R.id.chatcount);

        iDText.setText(listData.get(position).getiD());
        countText.setText(listData.get(position). getCount());

        return convertView;
    }
}
