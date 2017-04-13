package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mAuth= FirebaseAuth.getInstance();
        first_time_check();
        setContentView(R.layout.activity_main);
        topheader();

    }
    private boolean first_time_check() {
        SharedPreferences mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        String first = mPreferences.getString("loginid", null);
        if((first == null)){
            Intent i = new Intent(MainActivity.this,login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        return false;

    }
    public void addexp(View v)
    {
        Intent i=new Intent(this,addexpense.class);
        startActivity(i);
    }
    public void pay(View v)
    {
        Intent i=new Intent(this,pay.class);
        startActivity(i);
    }
    public void view(View v)
    {
        Intent i=new Intent(this,list.class);
        startActivity(i);
    }
    public void addlist(View v)
    {
        Intent i=new Intent(this,add_list.class);
        startActivity(i);
    }

    private void topheader()
    {

        SharedPreferences mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        String first = mPreferences.getString("loginid", null);
        if((first != null))
        {
            UserDB db=new UserDB(this);
        Cursor c=db.getdetails();
        if(c.getCount()!=0) {
            c.moveToFirst();
            TextView t = (TextView) findViewById(R.id.hello);
            TextView tx = (TextView) findViewById(R.id.wallet);
            t.setText("Hi," + c.getString(0).toString());
            tx.setText("Your Wallet Balance :Rs." + c.getString(1).toString());
        }
        }
    }
}

