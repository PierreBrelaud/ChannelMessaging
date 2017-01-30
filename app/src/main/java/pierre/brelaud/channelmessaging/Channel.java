package pierre.brelaud.channelmessaging;

/**
 * Created by brelaudp on 27/01/2017.
 */
public class Channel {
    public final String channelID;
    public final String name;
    public final String connectedusers;

    public Channel(String channelID, String name, String connectedusers) {
        this.channelID = channelID;
        this.name = name;
        this.connectedusers = connectedusers;
    }
}
