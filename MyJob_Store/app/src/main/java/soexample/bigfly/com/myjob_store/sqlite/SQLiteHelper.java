package soexample.bigfly.com.myjob_store.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/21   19:45<p>
 * <p>更改时间：2019/2/21   19:45<p>
 * <p>版本号：1<p>
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, "mydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table flow(id Integer primary key autoincrement, name VARCHAR UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
