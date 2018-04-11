package com.example.dz.androidlab;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class MessageFragment extends Fragment {

    TextView messageH;
    TextView messageID;
    Button deleteBtn;

    ListView chatlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_message_fragment,null);
        messageH = view.findViewById(R.id.msgHere);
        messageID = view.findViewById(R.id.msgID);
        deleteBtn = view.findViewById(R.id.delBtn);

        chatlist = getActivity().findViewById(R.id.chatView);

        final Bundle bundle = getArguments();
        final int currentID = bundle.getInt("Id");
        final boolean isTablet = bundle.getBoolean("Flag");

        if(bundle != null){
            messageH.setText(bundle.getString("message"));
            messageID.setText(String.valueOf(bundle.getInt("id")));
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                if(!isTablet) {
                    Toast.makeText(getActivity(),"On phone mode",Toast.LENGTH_LONG).show();
                    Intent temp = new Intent();
                    temp.putExtra("message", messageH.getText().toString());
                    getActivity().setResult(RESULT_OK, temp);
                    getActivity().finish();
                }
            }
        });

        return view;
    }
}
