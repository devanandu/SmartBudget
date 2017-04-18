package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class add_list extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    String date, first,timestamp;
    StringBuffer sb;
    Spinner spin;
    //CheckBox cb;
    SimpleDateFormat sdf;
    ArrayAdapter<String> adapter;
    list_itemsDB db;
    private List<String> array = new ArrayList<>();
    TextView text,type;
    EditText buylist;
    private List<members> memberses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_list);

        db = new list_itemsDB(this);

        spin = (Spinner) findViewById(R.id.list);
        spin.setOnItemSelectedListener(this);
        spin_load();

        member_fetch();

        sb = new StringBuffer();
        mAuth = FirebaseAuth.getInstance();
        buylist= (EditText) findViewById(R.id.displist);

        sdf = new SimpleDateFormat("EEE, MMM d, HH:mm:ss");

        SharedPreferences mPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid", null);

    }

    public void addlist(View v) {
        TextView listtext = (TextView) findViewById(R.id.displist);
        if (listtext.getText().length() != 0) {
            boolean any = false;
            for (int i = 0; i < memberses.size(); i++) {
                CheckBox c = (CheckBox) findViewById(i);
                if (c.isChecked()) {
                    //Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
                    any = true;
                    date = sdf.format(new Date());
                    sdf=new SimpleDateFormat("yyMMddHHmmssZ");
                    timestamp=sdf.format(new Date());
                    TextView p = (TextView) findViewById(R.id.phno);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("MESSAGES");
                    message m = new message(buylist.getText().toString(), first, date);
                    myRef.child(memberses.get(i).phno).child(timestamp).setValue(m);
                    myRef.child(memberses.get(i).phno).child("0status").child("new").setValue("false");
                    buylist.setText("");
                    sb.setLength(0);
                    Log.e("hi", "list added");
                }
            }
            if (!any)
                Toast.makeText(getApplicationContext(), "select any member", Toast.LENGTH_SHORT).show();
        }
    }
    public void viewbuylist(View v) {
        Intent i = new Intent(this, listview.class);
        startActivity(i);
    }

    public void spin_load() {

        Cursor c = db.get_all();
        if (c.getCount() == 0)
            Toast.makeText(getApplicationContext(), "Error..!! ", Toast.LENGTH_SHORT).show();
        else {
            while (c.moveToNext()) {
                array.add(c.getString(0));
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, array);
            spin.setAdapter(adapter);
        }
    }

    public void AddToList(View v) {
        if (spin.getSelectedItem().toString().equals("Other")) {
            text = (TextView) findViewById(R.id.notes);
            String newitem = text.getText().toString();
            if(!newitem.equals(""))
            {
                sb.append("* " + newitem + "..");
            text.setText("");
            text = (TextView) findViewById(R.id.quantityno);
            sb.append(text.getText());
            text = (TextView) findViewById(R.id.type);
            String type = text.getText().toString();
            sb.append(text.getText() + "\n");
            text = (TextView) findViewById(R.id.displist);
            text.setText(sb.toString());
            if(!db.add_other(newitem,type))
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            array.add(newitem);
            spin.setAdapter(adapter);}
        }
        else
            {
            text = (TextView) findViewById(R.id.notes);
            sb.append("* " + spin.getSelectedItem().toString() + ".." + text.getText().toString() + "..");
            text.setText("");
            text = (TextView) findViewById(R.id.quantityno);
            sb.append(text.getText());
            text = (TextView) findViewById(R.id.type);
            sb.append(text.getText() + "\n");
            text = (TextView) findViewById(R.id.displist);
            text.setText(sb.toString());
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text=(TextView)findViewById(R.id.notes);
        type=(TextView)findViewById(R.id.type);
        if(parent.getItemAtPosition(position).equals("Other"))
        {
            text.setHint("add new item..");

        }
        else
        {
            text.setHint("extra note..");

            Cursor c=db.get_type(spin.getSelectedItem().toString().trim());

            c.moveToLast();
            type.setText(c.getString(0));

            Log.e("hi",c.getString(0));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void member_fetch()
    {
        linkmembersDB db=new linkmembersDB(this);
        Cursor c=db.get_all();
        if(c.getCount()!=0)
        {
            while (c.moveToNext()) {
                members m = new members(c.getString(0), c.getString(1), c.getString(2));
                memberses.add(m);
            }
        //mAdapter.notifyDataSetChanged();
            LinearLayout l;
            l=(LinearLayout)findViewById(R.id.checkboxes);
            for(int i = 0; i <memberses.size(); i++) {
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setText(memberses.get(i).name+"("+memberses.get(i).phno+")");
                cb.setId(i);
                l.addView(cb);
            }
        }

    }
}