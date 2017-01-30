package pierre.brelaud.channelmessaging;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by brelaudp on 27/01/2017.
 */
public class ChannelAdapter extends ArrayAdapter<Channel> {
    public final Context context;
    public final Channels list;

    public ChannelAdapter(Context context, Channels list) {
        super(context, R.layout.rowlayout, list.channels);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView channel = (TextView) rowView.findViewById(R.id.textViewChannel);
        channel.setText(list.channels.get(position).name);

        TextView text = (TextView) rowView.findViewById(R.id.textViewText);
        text.setText("Nombre d'utilisateurs connectés : "+list.channels.get(position).connectedusers);

        return rowView;
    }

}
