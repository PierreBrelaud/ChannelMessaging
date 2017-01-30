package pierre.brelaud.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by brelaudp on 30/01/2017.
 */
public class MessageAdapter extends ArrayAdapter<Message>{
    public final Context context;
    public final Messages list;

    public MessageAdapter(Context context, Messages list) {
        super(context, R.layout.channel_list_messages, list.messages);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayoutmessage, parent, false);


        TextView message = (TextView) rowView.findViewById(R.id.tvNom);
        message.setText(list.messages.get(position).userID + " : " + list.messages.get(position).message);

        TextView text = (TextView) rowView.findViewById(R.id.tvDate);
        text.setText(list.messages.get(position).date);


        return rowView;
    }
}
