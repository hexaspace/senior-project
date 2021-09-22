package com.android.frontend;


import java.util.Date;

public class MessageItem {
    private String userId;
    private String content;

    public MessageItem(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    @Override
    public String toString() {
        return "MessageItem{" +
                "userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
