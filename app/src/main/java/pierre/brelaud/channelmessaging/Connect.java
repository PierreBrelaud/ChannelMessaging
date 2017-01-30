package pierre.brelaud.channelmessaging;

/**
 * Created by brelaudp on 27/01/2017.
 */
public class Connect {
    public final String response;
    public final String code;
    public final String accesstoken;


    public Connect(String response, String code, String accesstoken) {
        this.response = response;
        this.code = code;
        this.accesstoken = accesstoken;
    }
}
