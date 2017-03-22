package pierre.brelaud.channelmessaging.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import pierre.brelaud.channelmessaging.AsyncMethod;
import pierre.brelaud.channelmessaging.ChannelActivity;
import pierre.brelaud.channelmessaging.ChannelAdapter;
import pierre.brelaud.channelmessaging.ChannelListActivity;
import pierre.brelaud.channelmessaging.Channels;
import pierre.brelaud.channelmessaging.OnDownloadCompleteListener;
import pierre.brelaud.channelmessaging.R;

/**
 * Created by brelaudp on 27/02/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {
    ListView listView;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Channels ch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.channel_list_activity_fragment, container);
        listView = (ListView) v.findViewById(R.id.listView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken", "Yo");

        HashMap<String, String> h = new HashMap<>();
        h.put("accesstoken",accesstoken);

        AsyncMethod task = new AsyncMethod(h ,"http://www.raphaelbischof.fr/messaging/?function=getchannels",0);

        task.setOnDownloadCompleteListener(this);
        task.execute();

    }


    @Override
    public void onDownloadComplete(String result, int requestCode) {
        Gson gson = new Gson();
        ch = gson.fromJson(result, Channels.class);
        // R.layout.channel_show_channel,R.layout.rowlayout, ch.channels
        listView.setAdapter(new ChannelAdapter(getActivity(),ch));
        listView.setOnItemClickListener((ChannelListActivity)getActivity());


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(),ChannelActivity.class);
        myIntent.putExtra("id", ch.channels.get(position).channelID);
        startActivity(myIntent);
    }

    public Channels getCh() {
        return ch;
    }
}

