package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.FirebaseDatabase;

public class userDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
    }
    public void userdetails(View v)
    {
        UserDB db = new UserDB(this);
        TextView name = (TextView) findViewById(R.id.name);
        TextView bal = (TextView) findViewById(R.id.bal);
        SharedPreferences mPreferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        String first = mPreferences.getString("loginid", null);
        if (db.initial(name.getText().toString(),Integer.parseInt(bal.getText().toString()),first))
        {//Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
             }
        else
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
