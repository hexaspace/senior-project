package com.android.frontend.infected;



import com.google.gson.annotations.SerializedName;

public class InfectedItem {
    //event model에 따라 변경할것. 아직 미정임.
    @SerializedName("type")
    private String type; //아이템 타입 구분. 현재는 init 정했지만 추후 받아올것.
    @SerializedName("messageId")
    private String messageId; //_id
    //공통사항
    @SerializedName("location")
    private String location;
    @SerializedName("date")
    private String date;
    //event
    @SerializedName("time")
    private String time="";
    //infected
    @SerializedName("count")
    private int count=0;    //감염인원

    public InfectedItem(String type, String messageId, String location, String date, String time, int count) {
        this.type = type;
        this.messageId = messageId;
        this.location = location;
        this.date = date;
        this.time = time;
        this.count = count;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InfectedItem{" +
                "type='" + type + '\'' +
                ", messageId='" + messageId + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
