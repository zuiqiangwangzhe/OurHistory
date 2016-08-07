package ourhistory.hj.com.ourhistory.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ourhistory.hj.com.ourhistory.R;
import ourhistory.hj.com.ourhistory.db.MyDB;
import ourhistory.hj.com.ourhistory.java.AnimationProgressDialog;

/**
 * Created by Administrator on 2016/8/7.
 */
public class DetailActivity extends Activity {
    ImageView back;
    TextView textTitle;
    TextView textContent;
    ImageView collect;
    String id;
    String time;
    String title;
    String content;
    boolean isClick = true;
    MyDB myDB;
    SQLiteDatabase database;
    AnimationProgressDialog an;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 102:
                    textTitle.setText(title);
                    textContent.setText(content);
                    an.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        an = new AnimationProgressDialog(this);
        back = (ImageView) findViewById(R.id.detail_back);
        textTitle = (TextView) findViewById(R.id.detail_title);
        textContent = (TextView) findViewById(R.id.detail_content);
        collect = (ImageView) findViewById(R.id.detail_collect);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        time = intent.getStringExtra("time");
        back.setOnClickListener(onClickListener);
        collect.setOnClickListener(onClickListener);
        myDB = new MyDB(this);
        database = myDB.getReadableDatabase();
        database.execSQL("create table if not exists history(time varchar(100),title varchar(200),content varchar(10000))");
        Cursor cursor = database.query("history", null, "time=? and title=?", new String[]{time, title}, null, null, null);
        if (cursor.moveToFirst()) {
            content = cursor.getString(cursor.getColumnIndex("content"));
            textTitle.setText(title);
            textContent.setText(content);
            collect.setImageResource(R.mipmap.menu_icon_1_pressed);
            isClick = false;
        } else {
            getData();
        }
        cursor.close();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detail_back:
                    finish();
                    break;
                case R.id.detail_collect:
                    if (isClick) {
                        collect.setImageResource(R.mipmap.menu_icon_1_pressed);
                        isClick = false;
                        insert();
                    } else {
                        collect.setImageResource(R.mipmap.menu_icon_1_normal);
                        isClick = true;
                        remove();
                    }
                    break;
            }
        }
    };

    private void insert() {
        Cursor cursor = database.query("history", null, "time=? and title=?", new String[]{time, title}, null, null, null);
        if (cursor.moveToFirst() == false) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            contentValues.put("title", title);
            contentValues.put("content", content);
            database.insert("history", null, contentValues);
        }
        cursor.close();
    }

    private void remove() {
        database.delete("history", "time=? and title=?", new String[]{time, title});
    }

    private void getData() {
        an.show();
        String url = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=07a4bd17b7aaf93f4ef2fb99395b8fd3&e_id=" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(),"请检查网络或再次查询",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                detailJSON(result);
                handler.sendEmptyMessage(102);
            }
        });
    }

    private void detailJSON(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject obj = (JSONObject) jsonArray.get(0);
            content = obj.optString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        myDB.close();
    }
}
