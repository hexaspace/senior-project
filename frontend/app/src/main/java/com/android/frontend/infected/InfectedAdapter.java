package com.android.frontend.infected;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.frontend.R;

import java.util.ArrayList;

public class InfectedAdapter extends BaseAdapter {
    private static final String TAG = "infected";

    //아이템 데이터 리스트
    ArrayList<InfectedItem> infectedItemList = new ArrayList<InfectedItem>();
    // 아이템의 종류 분류에 따라 view 매칭
    private static final String ITEM_VIEW_TYPE_EVENT = "infectedEvent";
    private static final String ITEM_VIEW_TYPE_COUNT = "infectedCount";
//    private static final String ITEM_VIEW_TYPE_MAX = "2";

    public InfectedAdapter() {
    }

    @Override
    public int getCount() {
        return infectedItemList.size();
    }
    @Override
    public Object getItem(int position) {
        return infectedItemList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        String viewType = getItemType(position) ;

        if (convertView == null) {  //null else는 더 찾아볼것.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            // Data Set(listViewItemList)에서 리스트뷰의 몇번째 (position)에 위치한 데이터 참조 획득
            InfectedItem infectedItem = infectedItemList.get(position);


            switch (viewType) {
                case ITEM_VIEW_TYPE_EVENT:
                    //layout_event 을 이 클래스에 붙여준다. 바로 붙여줄 때는 true 설정
                    convertView = inflater.inflate(R.layout.layout_event, parent, false);
                    // layout_event에서 변경할 부분의 id를 갖고온다
                    TextView tv_location = (TextView) convertView.findViewById(R.id.tv_location);
                    TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                    TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    // 불러온 데이터를 setText해서 넣어준다.
                    tv_location.setText(infectedItem.getLocation());
                    tv_date.setText(infectedItem.getDate());
                    tv_time.setText(infectedItem.getTime());
                    break;
                case ITEM_VIEW_TYPE_COUNT:
                    convertView = inflater.inflate(R.layout.layout_infected, parent, false);

                    TextView tv_increased = (TextView) convertView.findViewById(R.id.tv_increased);

                    //iconImageView.setImageDrawable(listViewItem.getIcon());
                    String incresed = infectedItem.getLocation() + " 신규확진자 " ;//+infectedItem.getNum()+"명";
                    tv_increased.setText(incresed);
                    break;
            }
        }

        return convertView;
    }
    //기존 아이템 추가
    public void addItem(InfectedItem i){
        infectedItemList.add(i);
    }

    // EVENT 아이템 추가를 위한 함수.
    public void addItem(String location, String date, String time) {
        InfectedItem item = new InfectedItem() ;

        item.setType(ITEM_VIEW_TYPE_EVENT) ;
        item.setLocation(location);
        item.setDate(date);
        item.setTime(time);
        infectedItemList.add(item) ;
    }

    // COUNT 아이템 추가를 위한 함수.
//    public void addItem(String location, int num) {//Drawable icon,
//        InfectedItem item = new InfectedItem() ;
//
//        item.setType(ITEM_VIEW_TYPE_INFECTED) ;
////        item.setIcon(icon);
//        item.setLocation(location);
//        item.setNum(num);
//
//        infectedItemList.add(item);
//    }

    // type이 int가 아니라 사용불가
//    @Override
//    public int getItemViewType(int position) {
//        return infectedItemList.get(position).getType();
//    }
    public String getItemType(int position) {
        return infectedItemList.get(position).getType();
    }

}
