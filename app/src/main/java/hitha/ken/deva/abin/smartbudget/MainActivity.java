package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        first_time_check();
        setContentView(R.layout.activity_main);

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
    public void userupload(View v)
    {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser==null)
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        else
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("USERS");
            Toast.makeText(getApplicationContext(),firebaseUser.getEmail() , Toast.LENGTH_SHORT).show();
        user user = new user(firebaseUser.getUid(),firebaseUser.getEmail());
       myRef.child(firebaseUser.getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // successfully added user
                    Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                } else {
                    // failed to add user
                    Toast.makeText(getApplicationContext(), "error adding", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
    }
}

