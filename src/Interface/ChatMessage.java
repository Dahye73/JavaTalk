package Interface;

import java.sql.Timestamp; // Not java.security.Timestamp

public class ChatMessage {
    private String userId;
    private String message;
    private Timestamp timestamp;

    public ChatMessage(String userId, String message, Timestamp timestamp) {
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
