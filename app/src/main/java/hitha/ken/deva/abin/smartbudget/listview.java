package hitha.ken.deva.abin.smartbudget;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class listview extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    String date,first;
    StringBuffer sb;
    private RecyclerView recyclerView;
    private listadapter mAdapter;
    private List<message> msgs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        recyclerView = (RecyclerView) findViewById(R.id.buylist);

        mAdapter = new listadapter(msgs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        sb=new StringBuffer();
        mAuth= FirebaseAuth.getInstance();

        SharedPreferences mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid", null);
        first=first.substring(3);

        viewbuylist();
    }
    public void viewbuylist()
    {
        Log.e("hi", "inside");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("MESSAGES");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("hi", "starting fetching"+dataSnapshot.getValue()+first);
                if(dataSnapshot.hasChild(first)){
                    //TextView t=(TextView)findViewById(R.id.list);
                    Log.e("hi", "message exists");
                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.child(first).getChildren().iterator();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        message msg = dataSnapshotChild.getValue(message.class);
                        msgs.add(msg);
                        sb.append(msg.msg);
                    }
                    //t.setText(sb.toString());
                    Log.e("hi", "message :"+sb.toString());
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("hi", "oncancelled");
            }
        });
    }

}
