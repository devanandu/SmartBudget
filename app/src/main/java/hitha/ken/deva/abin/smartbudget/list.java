package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {
    private RecyclerView recyclerView;
    private transadapter mAdapter;
    private List<String> array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recy);

        mAdapter = new transadapter(array);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//recyclerView.setHasFixedSize(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            add();
            }
        });
        create();
    }
    public void add()
    {
        Intent i=new Intent(this,addexpense.class);
        startActivity(i);
    }
    public void create() {
        transactDB db = new transactDB(this);
        Cursor c = db.display();
        if (c.getCount() == 0)
            Toast.makeText(getApplicationContext(), "Error..!! nothing to display..!", Toast.LENGTH_SHORT).show();
        else {
            StringBuffer sb = new StringBuffer();
            while (c.moveToNext()) {
                sb.delete(0,sb.length());
                sb.append("\nID:" + c.getString(0) + "  TYPE:" + c.getString(2) + "\n");
                sb.append("CATEGORY:" + c.getString(3) + "  AMOUNT:Rs." + c.getString(1) + "\n");
                sb.append("NOTES:" + c.getString(4) + " TIME: " + c.getString(5));
                array.add(sb.toString());
               //Toast.makeText(getApplicationContext(),sb, Toast.LENGTH_SHORT).show();
            }

        }
     mAdapter.notifyDataSetChanged();
    }
public void onRestart()
{
  super.onRestart();
    finish();
    startActivity(getIntent());
}}
