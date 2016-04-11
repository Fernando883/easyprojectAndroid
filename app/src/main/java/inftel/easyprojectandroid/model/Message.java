package inftel.easyprojectandroid.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by csalas on 9/4/16.
 */
public class Message {
    public String action = "add";
    public String message;
    public String name;
    public String photoURL;
    public String email;
    public String timestamp;
    public String timestampWithoutFormat = null;

    public Message() {}

    public static Message fromJSON(String response) throws JSONException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Message message = new Message();
        JSONObject jsonObject = new JSONObject(response);
        message.message = jsonObject.getString("message");
        message.timestamp = simpleDateFormat.format(new Date(jsonObject.getLong("timestamp")));
        message.name = jsonObject.getString("userName");
        message.email = jsonObject.getString("userEmail");
        message.photoURL = jsonObject.getString("photoUrl");
        return message;
    }

    public int getViewType() {
        if ("carlos.salas@gmail.com".equals(this.email))
            return 0;
        //if (EasyProjectApp.getInstance().getUser().getEmail().equals(this.email))
        //    return 0;

        return 1;
    }


}
