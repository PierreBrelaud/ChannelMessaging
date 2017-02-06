package pierre.brelaud.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by brelaudp on 27/01/2017.
 */
public class ChannelListActivity extends Activity implements View.OnClickListener, OnDownloadCompleteListener, AdapterView.OnItemClickListener {

    private TextView textView;
    private ListView listView;
    private static final String PREFS_NAME = "MyPrefsFile";
    private Channels ch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_activity);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken", "Yo");

        HashMap<String, String> h = new HashMap<>();
        h.put("accesstoken",accesstoken);
        AsyncMethod task = new AsyncMethod(h, "http://www.raphaelbischof.fr/messaging/?function=getchannels", 2);
        task.setOnDownloadCompleteListener(this);
        task.execute();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)    {

        Toast.makeText(ChannelListActivity.this,"Connexion au channel : " + ch.channels.get(position).channelID, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), ChannelActivity.class);
        i.putExtra("channelid", ch.channels.get(position).channelID);
        startActivity(i);
    }

    @Override
    public void onDownloadComplete(String result, int type) {
        Gson g = new Gson();
        ch = g.fromJson(result, Channels.class);

        listView.setAdapter(new ChannelAdapter(getApplicationContext(),ch));
        listView.setOnItemClickListener(this);

    }

}
