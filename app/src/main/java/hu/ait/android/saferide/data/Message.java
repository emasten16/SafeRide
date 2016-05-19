package hu.ait.android.saferide.data;

/**
 * Created by emasten on 5/19/16.
 */
public class Message {
    private String toUser;
    private String messageText;

    public Message() {

    }

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
