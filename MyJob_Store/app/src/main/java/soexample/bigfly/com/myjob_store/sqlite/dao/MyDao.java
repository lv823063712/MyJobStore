package soexample.bigfly.com.myjob_store.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import soexample.bigfly.com.myjob_store.sqlite.SQLiteHelper;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   19:46<p>
 * <p>更改时间：2019/2/21   19:46<p>
 * <p>版本号：1<p>
 */

public class MyDao {
    private Context mContext;
    private final SQLiteHelper helper;
    private final SQLiteDatabase database;

    public MyDao(Context context) {
        mContext = context;
        helper = new SQLiteHelper(mContext);
        database = helper.getWritableDatabase();
    }

    //查询的方法
    public ArrayList<String> show() {
        ArrayList<String> datas = new ArrayList<>();
        Cursor cursor = database.query("flow", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            datas.add(name);
        }
        return datas;
    }

    //添加数据的方法
    public void insertall(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        database.insert("flow", null, values);
    }

    //删除数据的方法
    public void delete() {
        database.execSQL("delete from flow");
    }
}
