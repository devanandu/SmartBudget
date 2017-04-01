package hitha.ken.deva.abin.smartbudget;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by deva on 01/04/17.
 */

public class UserDB extends SQLiteOpenHelper {
    public static final String dbname="Smart Home budget";
    public static final String tbname="USER";
    public static final String username="name";
    public static final String balance="balance";
    public static final String phno="phno";

    public UserDB(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table USER(name text,balance integer,phno text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+tbname);
        onCreate(db);
    }
    public boolean initial(String name,int bal,String first)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();

        content.put(username,name);
        content.put(balance,bal);
        content.put(phno,first);
        return db.insert(tbname, null, content) != -1;
    }
}
