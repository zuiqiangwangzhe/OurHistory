package ourhistory.hj.com.ourhistory.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/7.
 */
public class MyDB extends SQLiteOpenHelper {
    private final static String NAME="historyData";
    private final static int VERSION=1;

    public MyDB(Context context){
        super(context,NAME,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
