package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class link extends AppCompatActivity {
    DatabaseReference myRef,req;
    String userphno;
    linkmembersDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
       db=new linkmembersDB(this);
        SharedPreferences mPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
         userphno= mPreferences.getString("loginid", null);
        ((Button)findViewById(R.id.contact)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
                Intent intent = new Intent(Intent.ACTION_PICK);
                // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        number=number.replace(" ","");
                        if(number.length()>10)
                        { number=number.substring(number.length()-10,number.length());}
                        String type = c.getString(1);
                        checkuser(type,number);
                        showSelectedNumber(type, number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }
    public void showSelectedNumber(String type, String number)
    {
        Toast.makeText(this, type + ": " + number, Toast.LENGTH_LONG).show();
    }
    public boolean checkuser(final String name,final String phno)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("USERS");
        req=database.getReference("REQUESTS");
        boolean status=false;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(phno)) {
                    Toast.makeText(getApplicationContext(), "User Available on Bilancio..Request send..", Toast.LENGTH_SHORT).show();
                    boolean request=false;
                    req.child(phno).child(userphno).setValue(request);
                    db.add_member(name,phno);
                }
                else
                {Toast.makeText(getApplicationContext(), "Sorry user not available", Toast.LENGTH_SHORT).show();
                    return;}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return status;
    }
}
