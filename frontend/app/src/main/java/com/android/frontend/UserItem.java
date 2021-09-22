package com.android.frontend;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserItem {
    /*
    * id: { type: String, unique: true }, // nickname
    name: { type: String, required: true }, //real name
    password: { type: String, required: true },
    color: { type: String, required: true }, //profile color
    createdAt: { type: Date, required: true }
    * */
    @SerializedName("userId")
    private String id;
    @SerializedName("username")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("createdAt")
    private Date createdAt;
    public UserItem(){


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
