package com.android.frontend;

import com.android.frontend.infected.InfectedItem;
import com.android.frontend.infected.InfectedResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    //로그인
    @POST("/api/users/login")
    Call<Void> executeLogin(@Body HashMap<String, String> map);
    //회원가입
    @POST("/api/users/register")
    Call<Void> executeRegister (@Body HashMap<String, String> map);
    // ID로 유저 정보 얻기
    @GET("/api/users/id/{id}")
    Call<UserItem> getUserById(@Path("id") String id);
    //모든 유저 얻기, list view 테스트용.
    @GET("/api/users/")
    Call<UserResponse> getAllUsers();
    // 모든 message얻기
    @POST("/api/messages/")
    Call<Void> createMessage(@Body MessageItem msg);
    // 모든 event얻기, event list 테스트용.
    @GET("/api/events")    //변수 서버에 맞게 수정예정
    Call<List<InfectedItem>> getAllEvents();
    // user id로 events 얻기, 추후 사용
    @GET("/api/events/eventId/{userId}")    //변수 서버에 맞게 수정예정
    Call<List<InfectedItem>> getEventsByUserId(@Path("userId") String userId);
    // _id 로 event 얻기. 테스트용
    @GET("/api/events/eventId/{id}")
    Call<InfectedItem> getEventsById(@Path("id") String id);

}
