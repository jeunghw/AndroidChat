package du.ac.jhw.dsctalk.UIFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import du.ac.jhw.dsctalk.R;
import du.ac.jhw.dsctalk.client.TalkClientSender;

public class LoginFragment extends Fragment {

    private TalkClientSender talkClientSender = TalkClientSender.getInstance();
    private EditText userIDText;
    private Button sendUserIDButton;
    private GetUserID getUserID;

    public interface GetUserID {
        void GetMYUserID(String myUserID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof GetUserID) {
            getUserID = (GetUserID) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getUserID = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View loginUI = inflater.inflate(R.layout.login, container, false);

        userIDText = loginUI.findViewById(R.id.user_id_text);
        sendUserIDButton = loginUI.findViewById(R.id.send_user_id_button);


        sendUserIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIDText.getText().toString();
                if(userID.getBytes().length <= 0) {
                    Toast.makeText(getActivity(), "아이디를 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else if(userID.getBytes().length < 50){
                    talkClientSender.sendLoginMessage(userID);
                    getUserID.GetMYUserID(userID);
                } else {
                    Toast.makeText(getActivity(), "아이디는 50자 이하로 해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return loginUI;
    }
}
