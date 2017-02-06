package pierre.brelaud.channelmessaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by brelaudp on 27/01/2017.
 */
public class ChannelActivity extends Activity implements View.OnClickListener, OnDownloadCompleteListener, AdapterView.OnItemClickListener {
    private static final String PREFS_NAME = "MyPrefsFile" ;
    private Button btnSend;
    private EditText etMessage;
    private LinearLayout llMessage;
    private ListView lvMessages;
    final Handler handler = new Handler();
    private String varToken;
    private String varId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_messages);

        lvMessages = (ListView) findViewById(R.id.listViewMessages);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        etMessage = (EditText) findViewById(R.id.etMessage);
        llMessage = (LinearLayout) findViewById(R.id.llMessage);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashMap<String, String> h = new HashMap<>();

        String token = settings.getString("accesstoken", "");
        String id = getIntent().getStringExtra("channelid");

        h.put("accesstoken",settings.getString("accesstoken","Yo"));
        h.put("channelid", getIntent().getStringExtra("channelid"));

        varId = id;
        varToken = token;


        AsyncMethod task = new AsyncMethod(h, "http://www.raphaelbischof.fr/messaging/?function=getmessages",1);
        task.setOnDownloadCompleteListener(this);
        task.execute();

    }

    @Override
    public void onClick(View v) {

        HashMap<String, String> h = new HashMap<>();
        h.put("accesstoken",varToken);
        h.put("channelid", varId);
        h.put("message", etMessage.getText().toString());

        etMessage.setText("");
        AsyncMethod task = new AsyncMethod(h, "http://www.raphaelbischof.fr/messaging/?function=sendmessage",2);
        task.setOnDownloadCompleteListener(this);
        task.execute();
    }

    @Override
    public void onDownloadComplete(String result, int type) {
        switch(type)
        {
            case 1:
                Gson g = new Gson();
                Messages m = g.fromJson(result, Messages.class);

                lvMessages.setAdapter(new MessageAdapter(getApplicationContext(), m));
                lvMessages.setOnItemClickListener(this);
                break;
            case 2 :
                break;
            default :
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

