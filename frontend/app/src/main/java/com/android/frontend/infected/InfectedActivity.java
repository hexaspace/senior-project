package com.android.frontend.infected;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.frontend.MessageItem;
import com.android.frontend.R;
import com.android.frontend.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfectedActivity extends AppCompatActivity {
    private static final String TAG = "infected";
    private static TextView tv_sender;
    private static TextView tv_content;
    private static TextView tv_sentAt;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static String userId;
    private ListView lv_infected;
    // activity가 처음 열릴때 시행되는 함수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infected);

        //id갖고오기 확인용 view
//        tv_sender = findViewById(R.id.tv_sender);
//        tv_content = findViewById(R.id.tv_content);
//        tv_sentAt = findViewById(R.id.tv_sentAt);
        lv_infected = (ListView) findViewById(R.id.lv_infected_list);

        //main activity에서 id값 받아오기
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        // 서버로부터 감염정보 리스트 받기
        GetInfectedList();

        Log.d(TAG, "get userid from loginActivity : "+ userId);
        Toast.makeText(InfectedActivity.this, userId+"님 로그인에 성공하셨습니다.",Toast.LENGTH_LONG).show();

        //receiver에서 받은 문자메세지 정보 받아오기 ---?
//        getReceiverIntent(intent);
    }
    // 새로운 문자메세지가 왔을 때 시행되는 함수
    @Override
    protected void onNewIntent(Intent intent) {    //현재 onCreate는 실행된 것, intent의 정보는 onNewIntent로 받아지기에 해결
        super.onNewIntent(intent);
        getReceiverIntent(intent);
    }
    // 문자메세지를 읽고 서버로 전송하는 함수
    public void getReceiverIntent(Intent intent){
        if(intent != null){
            String sender = intent.getStringExtra("sender");
            String content = intent.getStringExtra("content");
            // db 변경으로 보낸시각 생략
//            Date sentAt;
//            sentAt = (Date)intent.getSerializableExtra("sentAt");
//            if (sentAt != null){    //받게 될 때
//            String ssentAt = format.format(sentAt);
            //받은거 출력
            Log.d(TAG, "infected SMS : "+ sender+content);
//            Toast.makeText(InfectedActivity.this, "받은 재난문자 : "+content,Toast.LENGTH_LONG).show();
                if(sender.equals("#CMAS#Severe")){   //#CMAS#Severe 안드로이드 에뮬레이터 글자수 한계로 짤려서 테스트
                    // 재난문자 받는지 확인용 view
//                    tv_sender.setText(sender);
//                    tv_content.setText(content);
                    //서버로 전송
                    PostSms(userId, content);
                }
                else{   // 재난문자가 아닌 문자메세지일때
                }

        } else{
            finish();   //onNewIntent 종료
        }

    }

    // 기존의 저장된 문자마세지를 읽어옴
    public void BtnReadSms(View view){
        //sms read가 허가됬을때
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            //문자메세지를 가르킬 커서
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
            String sender;
            String content;
            // 디비 구조 변경으로 필요없음
//            String ssentAt;
//            Date sentAt;
            int i=0;
            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    //2 : sender 4(5) date, 12 content

                    sender = cursor.getString(2);
                    content = cursor.getString(12);

                    String msg = sender+content;
                    Log.d(TAG, "read msg : "+msg);
                    //화면에 보여주기

                    if(sender.equals("#CMAS#Severe")){   //#CMAS#Severe 안드로이드 에뮬레이터 글자수 한계로 짤려서 테스트
                        // 잘 읽는지  보여주기용 재난문자 받는지 확인용 view
//                        tv_sender.setText(sender);
//                        tv_content.setText(content);
                        //서버로 전송
                        PostSms(userId, content);
                    }
                    // use msgData
                    i=i+1;
                } while (cursor.moveToNext()&& i<3);// && i<1 최신 몇번째까지 읽을지 선택
            } else {
                // empty box, no SMS
            }

        }
    }
    // 받은 message 내용을 userId의 서버로 보내는 함수
    public void PostSms(String userId, String content){
        //server와 연결
        RetrofitClient retrofitClient = new RetrofitClient();
        //전달값을 MessageItem에 저장.
        MessageItem msg = new MessageItem(userId, content);
        Log.d(TAG, "message item : "+msg.toString());

        //excute login으로 post
        Call<Void> call = retrofitClient.server.createMessage(msg);
        //call의 결과 확인
        call.enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, String.valueOf(response.code()));
                if (response.code() == 201) {
//                    Toast.makeText(InfectedActivity.this, "infected] post msg successfully", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "infected] post msg successfully "+ response.code());
                } else if (response.code() == 400) {
//                    Toast.makeText(InfectedActivity.this, "infected] both content and sentAt are required",
//                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "infected] both content and sentAt are required"+ response.code());
                } else if (response.code() == 404) {
//                    Toast.makeText(InfectedActivity.this, "infected] ser not found",
//                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "infected] ser not found"+ response.code());
                }else if (response.code() == 500) {
                    Log.d(TAG, "infected]  500"+ response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(InfectedActivity.this, "infected] respond fail "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("infected","response fail"+ t.getMessage());
            }
        });
    }
    private void GetInfectedList(){// Inflate the layout for this fragment


        //통신
        RetrofitClient retrofitClient = new RetrofitClient();

        //data가 1개 일 때 InfectedItem 타입--------------------------------------------------------------
/*
        Call<InfectedItem> callItem = retrofitClient.server.getEventsById(id);      //데이터 1개라 InfectedItem 리스트 사용불가. 2개일때 확인필요 {}

//adapter생성
        InfectedAdapter infectedAdapter = new InfectedAdapter();;
        callItem.enqueue(new Callback<InfectedItem>() {
            @Override
            public void onResponse(Call<InfectedItem> call, Response<InfectedItem> response) {
                if(response.code() == 200){
                    Log.d(TAG, "get events 성공 : " + String.valueOf(response.code()));
//                  users와 다르게 userList : { 가 없이 바로 []배열 시작
                    InfectedItem items = response.body();
//                    for(InfectedItem item : items){
                        infectedAdapter.addItem(items);
//                        infectedAdapter.addItem(item.getLocation(),item.getDate(), item.getTime());
//                    }
                } else if (response.code() == 400) {
                    Log.d(TAG, "400응답 : "+String.valueOf(response.code()));
                }else if (response.code() == 404) {
                    Log.d(TAG, "events not found" + String.valueOf(response.code()));
                }
                //응답 후 list view adapter와 연동하여 데이터를 보여줘야한다.
                lv_infected.setAdapter(infectedAdapter);

            }
            @Override
            public void onFailure(Call<InfectedItem> call, Throwable t) {
                Log.d(TAG,"response fail"+t.toString());
                t.printStackTrace();
            }
        });

 */
        //data가 2개 이상 일 때 List<InfectedItem> 타입--------------------------------------------------------------
        Call<List<InfectedItem>> call = retrofitClient.server.getAllEvents();     //배열값 [ {}, {} ]

        //adapter생성
        InfectedAdapter infectedAdapter = new InfectedAdapter();;
        call.enqueue(new Callback<List<InfectedItem>>() {
            @Override
            public void onResponse(Call<List<InfectedItem>> call, Response<List<InfectedItem>> response) {
                if(response.code() == 200){
                    Log.d(TAG, "get events 성공 : " + String.valueOf(response.code()));
//                  users와 다르게 userList : { 가 없이 바로 []배열 시작
                    List<InfectedItem> items = response.body();
                    for(InfectedItem item : items){
                        infectedAdapter.addItem(item);
                    }
                } else if (response.code() == 400) {
                    Log.d(TAG, "400응답 : "+String.valueOf(response.code()));
                }else if (response.code() == 404) {
                    Log.d(TAG, "events not found" + String.valueOf(response.code()));
                }
                //응답 후 list view adapter와 연동하여 데이터를 보여줘야한다.
                lv_infected.setAdapter(infectedAdapter);

            }
            @Override
            public void onFailure(Call<List<InfectedItem>> call, Throwable t) {
                Log.d(TAG,"response fail"+t.toString());
                t.printStackTrace();
            }
        });
        /*
        // user id로 보내기--------------------------------------------------------------
        Call<List<InfectedItem>> callUserItem = retrofitClient.server.getEventsByUserId(userId);      //데이터 1개라 InfectedItem 리스트 사용불가. 2개일때 확인필요 {}

        //adapter생성
        InfectedAdapter infectedAdapter = new InfectedAdapter();;
        callUserItem.enqueue(new Callback<List<InfectedItem>>() {
            @Override
            public void onResponse(Call<List<InfectedItem>> call, Response<List<InfectedItem>> response) {
                if(response.code() == 200){
                    Log.d(TAG, "get events 성공 : " + String.valueOf(response.code()));
//                  users와 다르게 userList : { 가 없이 바로 []배열 시작
                    List<InfectedItem> items = response.body();
                    for(InfectedItem item : items){
                        infectedAdapter.addItem(item);
//                        infectedAdapter.addItem(item.getLocation(),item.getDate(), item.getTime());
                    }
                } else if (response.code() == 400) {
                    Log.d(TAG, "400응답 : "+String.valueOf(response.code()));
                }else if (response.code() == 404) {
                    Log.d(TAG, "events not found" + String.valueOf(response.code()));
                }
                //응답 후 list view adapter와 연동하여 데이터를 보여줘야한다.
                lv_infected.setAdapter(infectedAdapter);

            }
            @Override
            public void onFailure(Call<List<InfectedItem>> call, Throwable t) {
                Log.d(TAG,"response fail"+t.toString());
                t.printStackTrace();
            }
        });
*/
        //drauable 넣을땐 ContextCompat.getDrawable(this,R.drawable.apple)
//아이템 임의 추가
//        infectedAdapter.addItem("일원동 허브노래 연습장","03.01-03.02", "15:30-20:00");
//        infectedAdapter.addItem("남산타운5상가 1층 탑헤어","03.02", " ");
////        infectedAdapter.addItem("서울",138);
//        infectedAdapter.addItem("GS25편의점(산삼체육관역점)","03.01-03.03", "14:30-21:30");
//        infectedAdapter.addItem("산림 삼포스포렉스","03.02-03.03", " ");
//        infectedAdapter.addItem("청천동 철원양평해장국","03.03", "12:00-13:30");
//        infectedAdapter.addItem("왕십리",8);

    }

}