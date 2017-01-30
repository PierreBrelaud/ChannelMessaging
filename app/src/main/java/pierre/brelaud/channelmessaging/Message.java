package pierre.brelaud.channelmessaging;

import java.util.Date;

/**
 * Created by brelaudp on 30/01/2017.
 */
public class Message {
    public final String userID;
    public final String message;
    public final String date;
    public final String imageUrl;

    public Message(String userID, String message, String date, String imageUrl) {
        this.userID = userID;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
    }
}
