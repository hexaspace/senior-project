package com.android.frontend.infected;



import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class InfectedItem {
    //event model에 따라 변경할것. 아직 미정임.
    private String type = "infectedEvent"; //아이템 타입 구분. 현재는 init 정했지만 추후 받아올것.
    @SerializedName("_id")
    private String _id; //_id
    //공통사항
    @SerializedName("location")
    private String location;
    //event
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    //infected
//    @SerializedName("num")
//    private int num;    //감염인원

    public InfectedItem() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

//    public int getNum() {
//        return num;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }


    @Override
    public String toString() {
        return "InfectedItem{" +
                "type='" + type + '\'' +
                ", _id='" + _id + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
