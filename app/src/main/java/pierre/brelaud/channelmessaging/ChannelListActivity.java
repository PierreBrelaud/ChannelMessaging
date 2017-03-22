package pierre.brelaud.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import pierre.brelaud.channelmessaging.fragments.ChannelListFragment;
import pierre.brelaud.channelmessaging.fragments.MessageFragment;

/**
 * Created by brelaudp on 27/01/2017.
 */
public class ChannelListActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_show_channel);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)    {

        ChannelListFragment fragA   = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        Toast.makeText(ChannelListActivity.this,"Connexion au channel : " + fragA.getCh().channels.get(position).channelID, Toast.LENGTH_SHORT).show();

        MessageFragment fragB= (MessageFragment)getSupportFragmentManager().findFragmentById (R.id.fragmentB_ID);
        if(fragB==null|| !fragB.isInLayout()){
             Intent i = new Intent(getApplicationContext(), ChannelActivity.class);
             i.putExtra("channelid", fragA.getCh().channels.get(position).channelID);
             startActivity(i);
        }
        else
        {
            fragB.changeChannelId (Integer.parseInt(fragA.getCh().channels.get(position).channelID));
        }

    }


}
