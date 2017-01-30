package pierre.brelaud.channelmessaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_messages);

        lvMessages = (ListView) findViewById(R.id.listViewMessages);
        btnSend = (Button) findViewById(R.id.btnSend);
        etMessage = (EditText) findViewById(R.id.etMessage);
        llMessage = (LinearLayout) findViewById(R.id.llMessage);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashMap<String, String> h = new HashMap<>();
        h.put("accesstoken",settings.getString("accesstoken","Yo"));
        h.put("channelid", getIntent().getStringExtra("channelid"));

        AsyncLogin task = new AsyncLogin(getApplicationContext(), h, "http://www.raphaelbischof.fr/messaging/?function=getmessages");
        task.setOnDownloadCompleteListener(this);
        task.execute();

    }

    public void onClick(View v) {

    }

    @Override
    public void onDownloadComplete(String result) {
        Gson g = new Gson();
        Messages m = g.fromJson(result, Messages.class);
        lvMessages.setAdapter(new MessageAdapter(getApplicationContext(), m));
        lvMessages.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

