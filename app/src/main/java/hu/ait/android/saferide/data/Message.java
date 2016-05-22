package hu.ait.android.saferide.data;

/**
 * Created by emasten on 5/22/16.
 */
public class Message {

    public String toUser;
    public String messageText;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String message) {
        this.messageText = message;
    }
}
