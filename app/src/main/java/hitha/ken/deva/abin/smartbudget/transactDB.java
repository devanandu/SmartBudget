package hitha.ken.deva.abin.smartbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by deva on 07/03/17.
 */

public class transactDB extends SQLiteOpenHelper {
        public static final String dbname="Smart Home budget";
        public static final String tbname="Transactions";
        public static final String no="ID";
        public static final String amount="amount";
        public static final String notes="notes";
        public static final String type="type";
        public static final String cat="category";
        public transactDB(Context context) {
            super(context, dbname, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Transactions(id integer primary key,amount integer,type text,category text,notes text,created_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+tbname);
            onCreate(db);

        }
        public boolean addtransact(String typ,String catg,String amt,String note){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues content=new ContentValues();
            content.put(cat,catg);
            content.put(type,typ);
            content.put(amount,amt);
            content.put(notes,note);
            return db.insert(tbname, null, content) != -1;
        }
        public Cursor display()
        {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor result=db.rawQuery("select * from "+tbname,null);
            return result;
        }

}

