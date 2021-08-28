package com.android.frontend.infected;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfectedResponse { // 반환값의 구조에 따라 배열을 거치는 용도로 사용함. infected에선 사용안함.
    @SerializedName("InfectedList")
    public List<InfectedItem> items;
}
