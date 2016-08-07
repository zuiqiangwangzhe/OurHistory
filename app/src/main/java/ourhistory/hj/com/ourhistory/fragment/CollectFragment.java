package ourhistory.hj.com.ourhistory.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ourhistory.hj.com.ourhistory.R;
import ourhistory.hj.com.ourhistory.activity.DetailActivity;
import ourhistory.hj.com.ourhistory.adapter.EventAdapter;
import ourhistory.hj.com.ourhistory.db.MyDB;
import ourhistory.hj.com.ourhistory.java.EventInfo;

/**
 * Created by Administrator on 2016/8/7.
 */
public class CollectFragment extends Fragment {
    Context context;
    View view;
    ListView listView;
    TextView textView;
    List<EventInfo> list;
    MyDB myDB;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.collect_fragment_layout, container, false);
        listView = (ListView) view.findViewById(R.id.collect_fragment_list);
        textView = (TextView) view.findViewById(R.id.collect_fragment_text);
        setAdapter();
        listView.setOnItemClickListener(onItemClickListener);
        return view;
    }

    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            EventInfo eventInfo= (EventInfo) listView.getItemAtPosition(position);
            String time=eventInfo.getTime();
            String title=eventInfo.getContent();
            Intent intent=new Intent(context, DetailActivity.class);
            intent.putExtra("time",time);
            intent.putExtra("title",title);
            startActivity(intent);
        }
    };

    private void setAdapter() {
        myDB = new MyDB(context);
        sqLiteDatabase = myDB.getReadableDatabase();
        sqLiteDatabase.execSQL("create table if not exists history(time varchar(100),title varchar(200),content varchar(2000))");
        Cursor cursor = sqLiteDatabase.query("history", null, null, null, null, null, null);
        list = new ArrayList<>();
        int i = 1;
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            EventInfo eventInfo = new EventInfo();
            eventInfo.setNumber(i);
            eventInfo.setTime(time);
            eventInfo.setContent(title);
            list.add(eventInfo);
            i++;
        }
        if (list.size()==0) {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        cursor.close();
        EventAdapter eventAdapter = new EventAdapter(context, list);
        listView.setAdapter(eventAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        setAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sqLiteDatabase.close();
        myDB.close();
    }
}
