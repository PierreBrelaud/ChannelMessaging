package pierre.brelaud.channelmessaging.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import pierre.brelaud.channelmessaging.AsyncMethod;
import pierre.brelaud.channelmessaging.ChannelActivity;
import pierre.brelaud.channelmessaging.MessageAdapter;
import pierre.brelaud.channelmessaging.Messages;
import pierre.brelaud.channelmessaging.OnDownloadCompleteListener;
import pierre.brelaud.channelmessaging.R;

/**
 * Created by brelaudp on 22/03/2017.
 */
public class MessageFragment extends Fragment implements OnDownloadCompleteListener, View.OnClickListener {
    private static final int PICTURE_REQUEST_CODE = 1;
    private static final int INTENT_PHOTO = 0;
    public int id=-1;
    public Handler handler = new Handler();
    public static final String PREFS_NAME = "MyPrefsFile";
    public ListView listViewMessage;
    public Button valider;
    public EditText messageAEnvoyer;
    public Button photoBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.channel_message_fragment, container);

        listViewMessage = (ListView) v.findViewById(R.id.listViewMessages);
        valider = (Button) v.findViewById(R.id.btnSend);
        messageAEnvoyer = (EditText) v.findViewById(R.id.etMessage);
        valider.setOnClickListener((View.OnClickListener) this);

        return v;
    }

    public void changeChannelId(int channelId)
    {
        id = channelId;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        id = getActivity().getIntent().getIntExtra("id", -1);

        Runnable r = new Runnable() {
            public void run() {
                if(id != -1){
                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                    HashMap<String, String> infoConnexion = new HashMap<>();

                    infoConnexion.put("accesstoken",settings.getString("accesstoken",""));
                    infoConnexion.put("channelid",String.valueOf(id));

                    AsyncMethod login = new AsyncMethod(infoConnexion ,"http://www.raphaelbischof.fr/messaging/?function=getmessages", 2);
                    login.setOnDownloadCompleteListener(MessageFragment.this);
                    login.execute();
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r,1000);

    }


    @Override
    public void onDownloadComplete(String result, int type) {
        switch(type)
        {
            case 1:
                Gson g = new Gson();
                Messages m = g.fromJson(result, Messages.class);

                listViewMessage.setAdapter(new MessageAdapter(getActivity().getApplicationContext(), m));
                listViewMessage.setOnItemClickListener((AdapterView.OnItemClickListener) this);
                break;
            case 2 :
                break;
            default :
                break;

        }
    }


    @Override
    public void onClick(View v) {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        HashMap<String, String> h = new HashMap<>();
        h.put("accesstoken",settings.getString("accesstoken", ""));
        h.put("channelid", String.valueOf(id));
        h.put("message", messageAEnvoyer.getText().toString());

        messageAEnvoyer.setText("");
        AsyncMethod task = new AsyncMethod(h, "http://www.raphaelbischof.fr/messaging/?function=sendmessage",2);
        task.setOnDownloadCompleteListener(this);
        task.execute();
    }



}
