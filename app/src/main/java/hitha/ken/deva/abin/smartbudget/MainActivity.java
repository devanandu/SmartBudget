package hitha.ken.deva.abin.smartbudget;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    FirebaseAuth mAuth;
    SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mAuth= FirebaseAuth.getInstance();
        mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        String first = mPreferences.getString("loginid", null);
        first_time_check();
        setContentView(R.layout.activity_main);
        topheader();
        Intent intent = new Intent(this,checkrequests.class);
        if(!(isMyServiceRunning(checkrequests.class))&&first!=null)
        {
            Log.e("service:","started");
            this.startService(intent);
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e("service:","present");
                return true;
            }
        }
        Log.e("service:","absent");
        return false;
    }
    private boolean first_time_check() {

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

    public void addmember(View v)
    {
        Intent i=new Intent(this,link.class);
        startActivity(i);
    }
    private void topheader()
    {

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
    public void onResume(){
        super.onResume();

        String first = mPreferences.getString("loginid", null);
        if((first != null))
        topheader();
    }
}

