package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class product_list extends Activity {
    ArrayAdapter<String> Adapter;
    List<String> product=new ArrayList<>();
    private ListView lsitView;
    Cursor c;
    ImageView img;
    productdb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        lsitView =(ListView)findViewById(R.id.pro_list);
        img=(ImageView)findViewById(R.id.imageView2);
        populate();
    }

    private void populate() {
        db=new productdb(this);
        c=db.get_all();
        if(c.getCount()!=0)
        {
            StringBuffer sb = new StringBuffer();
            while (c.moveToNext())
            {
                sb.delete(0,sb.length());
                sb.append("\nProduct Name:" + c.getString(0) + "\nPurchased On:" + c.getString(1) + "\n");
                sb.append("Warranty Expires On:" + c.getString(2) + "\nService Details:" + c.getString(3) + "\n");
                sb.append("Purchased From:" + c.getString(4));
                product.add(sb.toString());
            }
            String[] list = new String[product.size()];
            list=product.toArray(list);
            Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            lsitView.setAdapter(Adapter);
            lsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c=db.get_all();
                    Log.e(position+"",c.getCount()+"");
                    c.moveToLast();
                    byte[] blob = c.getBlob(c.getColumnIndex("IMAGE"));
                    Bitmap bmp=Utils.getImage(blob);
                    img.setImageBitmap(bmp);
                    //Toast.makeText(product_list.this,position,Toast.LENGTH_SHORT).show();
                    Log.e(position+"",c.getCount()+"");
                }
            });
        }
    }
    public void add(View v)
    {
        Intent i=new Intent(this,Add_product.class);
        startActivity(i);
    }

}
