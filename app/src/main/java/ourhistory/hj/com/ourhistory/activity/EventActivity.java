package ourhistory.hj.com.ourhistory.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ourhistory.hj.com.ourhistory.R;
import ourhistory.hj.com.ourhistory.adapter.EventAdapter;
import ourhistory.hj.com.ourhistory.java.AnimationProgressDialog;
import ourhistory.hj.com.ourhistory.java.EventInfo;

/**
 * Created by Administrator on 2016/8/6.
 */
public class EventActivity extends Activity {
    ImageView back;
    ListView listView;
    TextView textView;
    TextView textTitle;
    String date;
    String time;
    String content;
    String id;
    List<EventInfo> list;
    AnimationProgressDialog an;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    EventAdapter adapter=new EventAdapter(getApplicationContext(),list);
                    listView.setAdapter(adapter);
                    if (list.size()==0){
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                    an.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        an=new AnimationProgressDialog(this);
        back= (ImageView) findViewById(R.id.event_back);
        listView= (ListView) findViewById(R.id.event_list);
        textView= (TextView) findViewById(R.id.event_text);
        textTitle= (TextView) findViewById(R.id.event_title);
        Intent intent=getIntent();
        String month=intent.getStringExtra("month");
        String day=intent.getStringExtra("day");
        textTitle.setText(month+"月"+day+"日发生的事件");
        date=month+"/"+day;
        getData();
        listView.setOnItemClickListener(onItemClickListener);
        back.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            EventInfo eventInfo= (EventInfo) listView.getItemAtPosition(position);
            String eventID=eventInfo.getId();
            time=eventInfo.getTime();
            content=eventInfo.getContent();
            Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
            intent.putExtra("id",eventID);
            intent.putExtra("time",time);
            intent.putExtra("title",content);
            startActivity(intent);
        }
    };

    private void getData(){
        an.show();
        String url="http://v.juhe.cn/todayOnhistory/queryEvent.php?key=07a4bd17b7aaf93f4ef2fb99395b8fd3&date="+date;
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(),"请检查网络或再次查询",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                simpleJSON(result);
                handler.sendEmptyMessage(101);
            }
        });
    }

    private void simpleJSON(String result){
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            list=new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject obj= (JSONObject) jsonArray.get(i);
                time=obj.optString("date");
                content=obj.optString("title");
                id=obj.optString("e_id");
                EventInfo eventInfo=new EventInfo();
                eventInfo.setNumber(i+1);
                eventInfo.setTime(time);
                eventInfo.setContent(content);
                eventInfo.setId(id);
                list.add(eventInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
